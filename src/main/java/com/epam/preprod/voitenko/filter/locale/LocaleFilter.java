package com.epam.preprod.voitenko.filter.locale;

import com.epam.preprod.voitenko.strategy.locale.CookieLocaleStrategy;
import com.epam.preprod.voitenko.strategy.locale.LocaleStrategy;
import com.epam.preprod.voitenko.strategy.locale.SessionLocaleStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.DESTROY_UNIMPLEMENTED;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.DEFAULT_LOCALE;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.LANG;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.LOCALE_STRATEGY;

public class LocaleFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(LocaleFilter.class);
    private Collection<Locale> appLocales;
    private LocaleStrategy localeStrategy;
    private Locale defaultLocale;
    private Map<String, LocaleStrategy> mapLocaleStrategy;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        defaultLocale = new Locale(filterConfig.getInitParameter(DEFAULT_LOCALE));
        initializeAppLocales(filterConfig);
        initializeMapLocaleStrategy();
        String keyStrategy = filterConfig.getInitParameter(LOCALE_STRATEGY);
        localeStrategy = getLocaleStrategy(keyStrategy);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequestWrapper localeSubstitutionRequestWrapper = getLocaleSubstitutionRequestWrapper((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);
        Locale selectedLocale = getLocale((HttpServletRequest) servletRequest);
        localeSubstitutionRequestWrapper = updateLocaleSubstitutionRequestWrapperIfNeed((HttpServletRequest) servletRequest, localeSubstitutionRequestWrapper, selectedLocale);
        servletResponse.setLocale(selectedLocale);
        filterChain.doFilter(localeSubstitutionRequestWrapper, servletResponse);
    }

    @Override
    public void destroy() {
        LOGGER.info(DESTROY_UNIMPLEMENTED);
    }

    private void initializeAppLocales(FilterConfig filterConfig) {
        appLocales = new HashSet<>();
        for (String nameParameter : Collections.list(filterConfig.getInitParameterNames())) {
            appLocales.add(new Locale(filterConfig.getInitParameter(nameParameter)));
        }
    }

    private void initializeMapLocaleStrategy() {
        mapLocaleStrategy = new HashMap<>();
        mapLocaleStrategy.put("cookie", new CookieLocaleStrategy());
        mapLocaleStrategy.put("session", new SessionLocaleStrategy());
    }

    private LocaleStrategy getLocaleStrategy(String keyStrategy) {
        if (mapLocaleStrategy.containsKey(keyStrategy)) {
            return mapLocaleStrategy.get(keyStrategy);
        } else {
            return new SessionLocaleStrategy();
        }
    }

    private HttpServletRequestWrapper getLocaleSubstitutionRequestWrapper(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Locale urlLocale = getLocaleFromUrl(httpServletRequest);
        if (appLocales.contains(urlLocale)) {
            localeStrategy.setLocale(urlLocale, httpServletRequest, httpServletResponse);
            if (localeStrategy instanceof CookieLocaleStrategy) {
                return new LocaleSubstitutionRequestWrapper(httpServletRequest, urlLocale);
            }
        }
        return null;
    }

    private Locale getLocale(HttpServletRequest httpServletRequest) {
        Locale selectedLocale = localeStrategy.getLocale(httpServletRequest);
        selectedLocale = getLocaleFromBrowserIfNeed(httpServletRequest, selectedLocale);
        selectedLocale = getDefaultLocaleIfNeed(selectedLocale);
        return selectedLocale;
    }

    private Locale getLocaleFromBrowserIfNeed(ServletRequest servletRequest, Locale oldValueLocale) {
        if (oldValueLocale == null) {
            return getAcceptableLocaleFromBrowser(Collections.list(servletRequest.getLocales()));
        }
        return oldValueLocale;
    }

    private Locale getDefaultLocaleIfNeed(Locale oldValueLocale) {
        if (oldValueLocale == null) {
            return defaultLocale;
        }
        return oldValueLocale;
    }

    private HttpServletRequestWrapper updateLocaleSubstitutionRequestWrapperIfNeed(HttpServletRequest request, HttpServletRequestWrapper oldValueReqWrapper, Locale selectedLocale) {
        if (oldValueReqWrapper == null) {
            return new LocaleSubstitutionRequestWrapper(request, selectedLocale);
        }
        return oldValueReqWrapper;
    }

    private Locale getLocaleFromUrl(HttpServletRequest httpServletRequest) {
        String lang = httpServletRequest.getParameter(LANG);
        if (lang != null && !lang.isEmpty()) {
            return new Locale(lang);
        }
        return null;
    }

    private Locale getAcceptableLocaleFromBrowser(Collection<Locale> userLocale) {
        Locale suitableLocale = null;
        int score = 0;
        for (Locale uLocale : userLocale) {
            for (Locale apLocale : appLocales) {
                int curScore = matchScore(uLocale, apLocale);
                if (curScore > score) {
                    score = curScore;
                    suitableLocale = apLocale;
                }
                if (score == 3) {
                    break;
                }
            }
        }
        return suitableLocale;
    }

    private int matchScore(Locale o1, Locale o2) {
        if (o1.equals(o2)) {
            return 3;
        } else if (o1.getLanguage().equals(o2.getLanguage()) && o1.getCountry().equals(o2.getCountry())) {
            return 2;
        } else if (o1.getLanguage().equals(o2.getLanguage())) {
            return 1;
        } else {
            return 0;
        }
    }
}