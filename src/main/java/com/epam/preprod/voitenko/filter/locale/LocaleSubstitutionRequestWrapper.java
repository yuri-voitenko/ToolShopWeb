package com.epam.preprod.voitenko.filter.locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;

public class LocaleSubstitutionRequestWrapper extends HttpServletRequestWrapper {
    private final Locale localeForReplacement;

    public LocaleSubstitutionRequestWrapper(HttpServletRequest request, Locale localeForReplacement) {
        super(request);
        this.localeForReplacement = localeForReplacement;
    }

    @Override
    public Locale getLocale() {
        return localeForReplacement;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return Collections.enumeration(Collections.singletonList(localeForReplacement));
    }
}
