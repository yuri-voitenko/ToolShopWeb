package com.epam.preprod.voitenko.listener;

import com.epam.preprod.voitenko.captchacleaner.CaptchaCleaner;
import com.epam.preprod.voitenko.strategy.CaptchaStrategy;
import com.epam.preprod.voitenko.strategy.CookieCaptchaStrategy;
import com.epam.preprod.voitenko.strategy.InputHiddenCaptchaStorage;
import com.epam.preprod.voitenko.strategy.SessionCaptchaStrategy;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;

public class InitListener implements ServletContextListener {
    private Map<String, CaptchaStrategy> strategyMap;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initialize();
        createStrategy(servletContextEvent);
        startCaptchaCleaner(servletContextEvent);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // NOP
    }

    private void startCaptchaCleaner(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        long timeout = Long.parseLong(servletContext.getInitParameter("Timeout"));
        Thread captchaCleaner = new Thread(new CaptchaCleaner(timeout));
        captchaCleaner.setDaemon(true);
        captchaCleaner.start();
    }

    private void createStrategy(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String nameStrategy = servletContext.getInitParameter("CaptchaStrategy");
        CaptchaStrategy captchaStrategy = new SessionCaptchaStrategy();         // default
        if (strategyMap.containsKey(nameStrategy)) {
            captchaStrategy = strategyMap.get(nameStrategy);
        }
        servletContext.setAttribute("strategy", captchaStrategy);
    }

    private void initialize() {
        strategyMap = new HashMap<>();
        strategyMap.put("session", new SessionCaptchaStrategy());
        strategyMap.put("cookie", new CookieCaptchaStrategy());
        strategyMap.put("hidden", new InputHiddenCaptchaStorage());
    }
}