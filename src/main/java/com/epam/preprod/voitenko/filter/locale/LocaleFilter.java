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
import java.util.HashSet;
import java.util.Locale;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.DEFAULT_LOCALE;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.LANG;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.LOCALE_STRATEGY;
import static com.epam.preprod.voitenko.constant.Constatns.Message.DESTROY_UNIMPLEMENTED;

public class LocaleFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(LocaleFilter.class);
    private Collection<Locale> locales;
    private LocaleStrategy localeStrategy;
    private Locale defaultLocale;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        defaultLocale = new Locale(filterConfig.getInitParameter(DEFAULT_LOCALE));
        locales = new HashSet<>();
        for (String nameParameter : Collections.list(filterConfig.getInitParameterNames())) {
            locales.add(new Locale(filterConfig.getInitParameter(nameParameter)));
        }
        String keyStrategy = filterConfig.getInitParameter(LOCALE_STRATEGY);
        localeStrategy = getLocaleStrategy(keyStrategy);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        Locale selectedLocale;

        HttpServletRequestWrapper localeSubstitutionRequestWrapper = getLocaleSubstitutionRequestWrapper(httpServletRequest, httpServletResponse);
        selectedLocale = localeStrategy.getLocale(httpServletRequest);

        if (selectedLocale == null) {
            selectedLocale = getAcceptableLocale(Collections.list(servletRequest.getLocales()));
        }

        if (selectedLocale == null) {
            selectedLocale = defaultLocale;
        }
        httpServletResponse.setLocale(selectedLocale);
        if (localeSubstitutionRequestWrapper == null) {
            localeSubstitutionRequestWrapper = new LocaleSubstitutionRequestWrapper(httpServletRequest, selectedLocale);
        }
        filterChain.doFilter(localeSubstitutionRequestWrapper, httpServletResponse);
    }

    @Override
    public void destroy() {
        LOGGER.info(DESTROY_UNIMPLEMENTED);
    }

    private LocaleStrategy getLocaleStrategy(String keyStrategy) {
        if (keyStrategy == null) {
            return new SessionLocaleStrategy();
        }
        switch (keyStrategy) {
            case "cookie":
                return new CookieLocaleStrategy();
            case "session":
            default:
                return new SessionLocaleStrategy();
        }
    }

    private HttpServletRequestWrapper getLocaleSubstitutionRequestWrapper(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Locale urlLocale = getLocaleFromUrl(httpServletRequest);

        if (locales.contains(urlLocale)) {
            localeStrategy.setLocale(urlLocale, httpServletRequest, httpServletResponse);
            if (localeStrategy instanceof CookieLocaleStrategy) {
                return new LocaleSubstitutionRequestWrapper(httpServletRequest, urlLocale);
            }
        }
        return null;
    }

    private Locale getLocaleFromUrl(HttpServletRequest httpServletRequest) {
        String lang = httpServletRequest.getParameter(LANG);
        if (lang != null && !lang.isEmpty()) {
            return new Locale(lang);
        }
        return null;
    }

    private Locale getAcceptableLocale(Collection<Locale> userLocale) {
        Locale suitableLocale = null;
        int score = 0;
        for (Locale uLocale : userLocale) {
            for (Locale apLocale : locales) {
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
        int score = 0;
        if (o1.getLanguage().equals(o2.getLanguage())) {
            score = 1;
            if (o1.getCountry().equals(o2.getCountry())) {
                score = 2;
                if (o1.getVariant().equals(o2.getVariant())) {
                    score = 3;
                }
            }
        }
        return score;
    }
}