package com.epam.preprod.voitenko.filter.access;

import com.epam.preprod.voitenko.entity.Role;
import com.epam.preprod.voitenko.entity.UserEntity;
import com.epam.preprod.voitenko.util.Authenticator;
import com.epam.preprod.voitenko.util.SecurityXMLParserUtil;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import static com.epam.preprod.voitenko.constant.Constatns.Exceptions.DESTROY_UNIMPLEMENTED;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.PATH_TO_SECURITY_FILE_XML;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.USER_ENTITY;

public class AccessVerifyFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(AccessVerifyFilter.class);
    private Authenticator authenticator;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String path = filterConfig.getServletContext().getInitParameter(PATH_TO_SECURITY_FILE_XML);
        Map<String, Collection<String>> permissionMap = SecurityXMLParserUtil.parse(path);
        authenticator = new Authenticator(permissionMap);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        if (httpServletRequest.getHeader("Accept").contains("text/html")) {
            verifyAccess(filterChain, httpServletRequest, httpServletResponse);
        } else {
            filterChain.doFilter(httpServletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        LOGGER.info(DESTROY_UNIMPLEMENTED);
    }

    private void verifyAccess(FilterChain filterChain, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        String requestURI = httpServletRequest.getRequestURI();
        Role role = getRole(httpServletRequest.getSession());
        if (!authenticator.isRoleHaveAccess(role, requestURI)) {
            httpServletResponse.sendRedirect("/accessDenied");
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    private Role getRole(HttpSession session) {
        UserEntity userEntity = (UserEntity) session.getAttribute(USER_ENTITY);
        if (userEntity == null) {
            return Role.ANYONE;
        } else {
            return userEntity.getRole();
        }
    }
}