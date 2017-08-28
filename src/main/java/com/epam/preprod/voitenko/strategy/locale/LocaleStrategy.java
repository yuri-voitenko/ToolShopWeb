package com.epam.preprod.voitenko.strategy.locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public interface LocaleStrategy {
    Locale getLocale(HttpServletRequest request);

    void setLocale(Locale newLocale, HttpServletRequest request, HttpServletResponse response);
}