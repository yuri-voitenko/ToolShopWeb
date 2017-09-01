package com.epam.preprod.voitenko.filter.gzip;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.DESTROY_UNIMPLEMENTED;
import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.INIT_UNIMPLEMENTED;
import static com.epam.preprod.voitenko.constant.Constatns.GZIP;

public class GZipServletFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(GZipServletFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info(INIT_UNIMPLEMENTED);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (acceptsGZipEncoding((HttpServletRequest) request)) {
            chainDoFilterWithGZipEncoding(request, chain, (HttpServletResponse) response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        LOGGER.info(DESTROY_UNIMPLEMENTED);
    }

    private boolean acceptsGZipEncoding(HttpServletRequest httpRequest) {
        String acceptEncoding = httpRequest.getHeader("Accept-Encoding");
        return acceptEncoding != null && acceptEncoding.contains(GZIP);
    }

    private void chainDoFilterWithGZipEncoding(ServletRequest request, FilterChain chain, HttpServletResponse httpResponse) throws IOException, ServletException {
        httpResponse.addHeader("Content-Encoding", GZIP);
        GZipServletResponseWrapper gzipResponse = new GZipServletResponseWrapper(httpResponse);
        chain.doFilter(request, gzipResponse);
        gzipResponse.close();
    }
}