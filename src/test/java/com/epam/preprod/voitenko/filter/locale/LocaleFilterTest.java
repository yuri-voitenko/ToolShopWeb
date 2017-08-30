package com.epam.preprod.voitenko.filter.locale;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static com.epam.preprod.voitenko.constant.Constatns.EMPTY_STRING;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class LocaleFilterTest {
    @Mock
    private FilterConfig mockFilterConfig;
    @Mock
    private HttpServletRequest mockHttpServletRequest;
    @Mock
    private HttpServletResponse mockHttpServletResponse;
    @Mock
    private HttpSession mockHttpSession;
    @Mock
    private FilterChain mockFilterChain;
    @Mock
    private ServletContext mockServletContext;

    private LocaleFilter localeFilter;
    private Map<String, String> initParameters;
    private Collection<Locale> browserLocales;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        localeFilter = new LocaleFilter();
        initParameters = new HashMap<>();
        browserLocales = new LinkedHashSet<>();

        initParameters.put("UK", "uk");
        initParameters.put("EN", "en");
        initParameters.put("RU", "ru");

        browserLocales.add(new Locale("ar"));
        browserLocales.add(new Locale("be"));
        browserLocales.add(new Locale("en", "US"));
        browserLocales.add(new Locale("en"));
        browserLocales.add(new Locale("uk"));
        browserLocales.add(new Locale("ru"));

        when(mockFilterConfig.getInitParameter(LOCALE_STRATEGY)).thenReturn("session");
        when(mockFilterConfig.getInitParameter(DEFAULT_LOCALE)).thenReturn("uk");
        when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
        when(mockHttpServletRequest.getServletContext()).thenReturn(mockServletContext);
    }

    @Test
    public void doFilterShouldSetDefaultLocale() throws ServletException, IOException {
        when(mockFilterConfig.getInitParameter(DEFAULT_LOCALE)).thenReturn("en");
        initParameters.clear();
        browserLocales.clear();
        setFilterInitParameters();
        when(mockHttpServletRequest.getLocales()).thenReturn(Collections.enumeration(browserLocales));

        localeFilter.init(mockFilterConfig);
        localeFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
        verify(mockHttpServletResponse).setLocale(new Locale("en"));
    }

    @Test
    public void doFilterShouldSetDefaultLocaleWhenNotAcceptableLocale() throws ServletException, IOException {
        when(mockFilterConfig.getInitParameter(DEFAULT_LOCALE)).thenReturn("ru");
        initParameters.clear();
        initParameters.put("UK", "uk");
        browserLocales.clear();
        browserLocales.add(new Locale("en"));
        setFilterInitParameters();
        when(mockHttpServletRequest.getLocales()).thenReturn(Collections.enumeration(browserLocales));

        localeFilter.init(mockFilterConfig);
        localeFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
        verify(mockHttpServletResponse).setLocale(new Locale("ru"));
    }

    @Test
    public void doFilterShouldSetBrowserLocaleWhenPartiallyAcceptableLocale() throws ServletException, IOException {
        setFilterInitParameters();
        when(mockHttpServletRequest.getLocales()).thenReturn(Collections.enumeration(browserLocales));

        localeFilter.init(mockFilterConfig);
        localeFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
        verify(mockHttpServletResponse).setLocale(new Locale("en"));
    }

    @Test
    public void doFilterShouldSetBrowserLocaleWhenFullAcceptableLocale() throws ServletException, IOException {
        browserLocales.remove(new Locale("en", "US"));
        setFilterInitParameters();
        when(mockHttpServletRequest.getLocales()).thenReturn(Collections.enumeration(browserLocales));

        localeFilter.init(mockFilterConfig);
        localeFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
        verify(mockHttpServletResponse).setLocale(new Locale("en"));
    }

    @Test
    public void doFilterShouldSetDefaultLocaleWhenUrlLangParameterIsEmpty() throws ServletException, IOException {
        initParameters.clear();
        browserLocales.clear();
        setFilterInitParameters();
        when(mockHttpServletRequest.getLocales()).thenReturn(Collections.enumeration(browserLocales));
        when(mockHttpServletRequest.getParameter(LANG)).thenReturn(EMPTY_STRING);

        localeFilter.init(mockFilterConfig);
        localeFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
        verify(mockHttpServletResponse).setLocale(new Locale("uk"));
    }

    @Test
    public void doFilterShouldSetDefaultLocaleWhenLocaleFromUrlNotSupportApp() throws ServletException, IOException {
        browserLocales.clear();
        setFilterInitParameters();
        when(mockHttpServletRequest.getLocales()).thenReturn(Collections.enumeration(browserLocales));
        when(mockHttpServletRequest.getParameter(LANG)).thenReturn("de");

        localeFilter.init(mockFilterConfig);
        localeFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
        verify(mockHttpServletResponse).setLocale(new Locale("uk"));
    }

    @Test
    public void doFilterShouldSetUrlLocaleWhenStorageSession() throws ServletException, IOException {
        browserLocales.clear();
        setFilterInitParameters();
        when(mockHttpServletRequest.getLocales()).thenReturn(Collections.enumeration(browserLocales));
        when(mockHttpServletRequest.getParameter(LANG)).thenReturn("ru");

        localeFilter.init(mockFilterConfig);
        localeFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
        verify(mockHttpSession).setAttribute(SELECTED_LOCALE, new Locale("ru"));
    }

    @Test
    public void doFilterShouldSetUrlLocaleWhenStorageCookie() throws ServletException, IOException {
        browserLocales.clear();
        setFilterInitParameters();

        when(mockServletContext.getInitParameter(COOKIE_MAX_AGE)).thenReturn("5");
        when(mockFilterConfig.getInitParameter(LOCALE_STRATEGY)).thenReturn("cookie");
        when(mockHttpServletRequest.getLocales()).thenReturn(Collections.enumeration(browserLocales));
        when(mockHttpServletRequest.getParameter(LANG)).thenReturn("ru");

        localeFilter.init(mockFilterConfig);
        localeFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
        verify(mockHttpServletResponse, times(1)).addCookie(any(Cookie.class));
    }

    private void setFilterInitParameters() {
        when(mockFilterConfig.getInitParameterNames()).thenReturn(Collections.enumeration(initParameters.keySet()));
        for (String key : initParameters.keySet()) {
            when(mockFilterConfig.getInitParameter(key)).thenReturn(initParameters.get(key));
        }
    }
}