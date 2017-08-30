package com.epam.preprod.voitenko.servlet.user;

import com.epam.preprod.voitenko.servlet.user.Logout;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.USER_ENTITY;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class LogoutTest {
    @Mock
    private HttpServletRequest mockHttpServletRequest;
    @Mock
    private HttpServletResponse mockHttpServletResponse;
    @Mock
    private HttpSession mockHttpSession;
    @Mock
    private RequestDispatcher mockRequestDispatcher;

    @Before
    public void setUp() {
        initMocks(this);
        when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
        when(mockHttpServletRequest.getRequestDispatcher(anyString())).thenReturn(mockRequestDispatcher);
    }

    @Test
    public void serviceShouldRemoveUserFromSessionAndForwardToHomePage() throws ServletException, IOException {
        new Logout().service(mockHttpServletRequest, mockHttpServletResponse);
        verify(mockHttpSession, times(1)).removeAttribute(USER_ENTITY);
        verify(mockHttpServletRequest.getRequestDispatcher("/viewHomePage"))
                .forward(mockHttpServletRequest, mockHttpServletResponse);
    }
}