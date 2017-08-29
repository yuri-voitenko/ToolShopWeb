package com.epam.preprod.voitenko.filter.cache;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PreventCacheFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        httpServletResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        httpServletResponse.setDateHeader("Expires", 0); // Proxies.
        filterChain.doFilter(servletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {

    }
}