package com.epam.preprod.voitenko.strategy.locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.SELECTED_LOCALE;

public class CookieLocaleStrategy implements LocaleStrategy {
    @Override
    public Locale getLocale(HttpServletRequest request) {
        Locale selectedLocale = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(SELECTED_LOCALE)) {
                    selectedLocale = new Locale(cookie.getValue());
                }
            }
        }
        return selectedLocale;
    }

    @Override
    public void setLocale(Locale newLocale, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(SELECTED_LOCALE, newLocale.toString());
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}