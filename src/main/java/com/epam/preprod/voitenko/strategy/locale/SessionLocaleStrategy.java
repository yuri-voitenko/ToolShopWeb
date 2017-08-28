package com.epam.preprod.voitenko.strategy.locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.SELECTED_LOCALE;

public class SessionLocaleStrategy implements LocaleStrategy {
    @Override
    public Locale getLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Locale) session.getAttribute(SELECTED_LOCALE);
    }

    @Override
    public void setLocale(Locale newLocale, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute(SELECTED_LOCALE, newLocale);
    }
}