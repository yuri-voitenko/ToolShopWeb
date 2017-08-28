package com.epam.preprod.voitenko.servlet.order;

import com.epam.preprod.voitenko.entity.InfoOrderedToolEntity;
import com.epam.preprod.voitenko.entity.UserEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static com.epam.preprod.voitenko.constant.Constatns.Message.UNAUTHORIZED_USER;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ViewOrderTest {
    @Mock
    private HttpServletRequest mockHttpServletRequest;
    @Mock
    private HttpServletResponse mockHttpServletResponse;
    @Mock
    private HttpSession mockHttpSession;
    @Mock
    private RequestDispatcher mockRequestDispatcher;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
        when(mockHttpServletRequest.getRequestDispatcher(anyString())).thenReturn(mockRequestDispatcher);
    }

    @Test
    public void serviceShouldForwardToViewLoginServlet() throws ServletException, IOException {
        when(mockHttpSession.getAttribute(USER_ENTITY)).thenReturn(null);
        new ViewOrder().service(mockHttpServletRequest, mockHttpServletResponse);
        Map<String, String> errors = new HashMap<>();
        errors.put(USER_ENTITY, UNAUTHORIZED_USER);
        verify(mockHttpServletRequest).setAttribute(ERRORS, errors);
        verify(mockHttpServletRequest.getRequestDispatcher("/viewLoginForm"))
                .forward(mockHttpServletRequest, mockHttpServletResponse);
    }

    @Test
    public void serviceShouldForwardToViewOrderServlet() throws ServletException, IOException {
        when(mockHttpSession.getAttribute(USER_ENTITY)).thenReturn(new UserEntity());
        new ViewOrder().service(mockHttpServletRequest, mockHttpServletResponse);
        verify(mockHttpSession).setAttribute(LIST_ORDERED_TOOLS, new ArrayList<InfoOrderedToolEntity>());
        verify(mockHttpServletRequest.getRequestDispatcher("/WEB-INF/order.jsp"))
                .forward(mockHttpServletRequest, mockHttpServletResponse);
    }
}