package com.epam.preprod.voitenko.strategy.locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.COOKIE_MAX_AGE;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.SELECTED_LOCALE;

public class CookieLocaleStrategy implements LocaleStrategy {
    @Override
    public Locale getLocale(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return getLocaleFromCookie(cookies);
        }
        return null;
    }

    @Override
    public void setLocale(Locale newLocale, HttpServletRequest request, HttpServletResponse response) {
        int cookieMaxAge = Integer.parseInt(request.getServletContext().getInitParameter(COOKIE_MAX_AGE));
        Cookie cookie = new Cookie(SELECTED_LOCALE, newLocale.toString());
        cookie.setMaxAge(cookieMaxAge);
        response.addCookie(cookie);
    }

    private Locale getLocaleFromCookie(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(SELECTED_LOCALE)) {
                return new Locale(cookie.getValue());
            }
        }
        return null;
    }
}