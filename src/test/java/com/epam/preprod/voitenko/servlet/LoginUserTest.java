package com.epam.preprod.voitenko.servlet;

import com.epam.preprod.voitenko.entity.LoginEntity;
import com.epam.preprod.voitenko.handler.DataSourceHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static com.epam.preprod.voitenko.constant.Constatns.Message.HINT_NOT_EMPTY_FIELD;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class LoginUserTest {
    @Mock
    private HttpServletRequest mockHttpServletRequest;
    @Mock
    private HttpServletResponse mockHttpServletResponse;
    @Mock
    private HttpSession mockHttpSession;
    @Mock
    private RequestDispatcher mockRequestDispatcher;

    private LoginUser loginUser;
    private LoginEntity emptyLogEntity;

    @Before
    public void setUp() {
        initMocks(this);
        loginUser = new LoginUser();
        emptyLogEntity = new LoginEntity();
        when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
        when(mockHttpServletRequest.getRequestDispatcher(anyString())).thenReturn(mockRequestDispatcher);
    }

    @Test
    public void doGetShouldForwardToViewLoginServlet() throws ServletException, IOException {
        loginUser.doGet(mockHttpServletRequest, mockHttpServletResponse);
        verify(mockHttpServletRequest.getRequestDispatcher("/viewLoginForm"))
                .forward(mockHttpServletRequest, mockHttpServletResponse);
        when(mockHttpSession.getAttribute(anyString())).thenReturn(null);
        doNothing().when(mockHttpSession).removeAttribute(anyString());
        doNothing().when(mockHttpServletRequest).setAttribute(anyString(), anyObject());
    }

    @Test
    public void doPostShouldRedirectToLoginUserServletWhenAllFieldsIsEmpty() throws ServletException, IOException {
        loginUser.doPost(mockHttpServletRequest, mockHttpServletResponse);
        verify(mockHttpSession).setAttribute(LOGIN_ENTITY, emptyLogEntity);
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put(EMAIL, HINT_NOT_EMPTY_FIELD);
        errors.put(PASSWORD, HINT_NOT_EMPTY_FIELD);
        verify(mockHttpSession).setAttribute(ERRORS, errors);
        verify(mockHttpServletResponse).sendRedirect("/loginUser");
    }
}