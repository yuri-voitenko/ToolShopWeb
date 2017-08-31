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

import static com.epam.preprod.voitenko.constant.Constatns.GZIP;
import static com.epam.preprod.voitenko.constant.Constatns.Message.DESTROY_UNIMPLEMENTED;
import static com.epam.preprod.voitenko.constant.Constatns.Message.INIT_UNIMPLEMENTED;

public class GZipServletFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(GZipServletFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info(INIT_UNIMPLEMENTED);
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (acceptsGZipEncoding(httpRequest)) {
            httpResponse.addHeader("Content-Encoding", GZIP);
            GZipServletResponseWrapper gzipResponse = new GZipServletResponseWrapper(httpResponse);
            chain.doFilter(request, gzipResponse);
            gzipResponse.close();
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
}