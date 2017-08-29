package com.epam.preprod.voitenko.filter.locale;

import com.epam.preprod.voitenko.strategy.locale.CookieLocaleStrategy;
import com.epam.preprod.voitenko.strategy.locale.LocaleStrategy;
import com.epam.preprod.voitenko.strategy.locale.SessionLocaleStrategy;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;

public class LocaleFilter implements Filter {
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
        switch (filterConfig.getInitParameter(LOCALE_STRATEGY)) {
            case "cookie":
                localeStrategy = new CookieLocaleStrategy();
                break;
            case "session":
            default:
                localeStrategy = new SessionLocaleStrategy();
                break;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        final HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        Locale selectedLocale;
        // First check url parameter
        String lang = servletRequest.getParameter(LANG);
        if (lang != null && !lang.isEmpty()) {
            Locale urlLocale = new Locale(lang);
            if (locales.contains(urlLocale)) {
                localeStrategy.setLocale(urlLocale, httpServletRequest, httpServletResponse);
            }
        }
        selectedLocale = localeStrategy.getLocale(httpServletRequest);
        // if not url parameter, get locale from browser
        if (selectedLocale == null) {
            selectedLocale = getAcceptableLocale(Collections.list(servletRequest.getLocales()));
        }
        // if not acceptable locale, set default
        if (selectedLocale == null) {
            selectedLocale = defaultLocale;
        }
        HttpServletRequestWrapper localeSubstitutionRequestWrapper = new LocaleSubstitutionRequestWrapper(httpServletRequest, selectedLocale);
        filterChain.doFilter(localeSubstitutionRequestWrapper, httpServletResponse);
    }

    @Override
    public void destroy() {
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