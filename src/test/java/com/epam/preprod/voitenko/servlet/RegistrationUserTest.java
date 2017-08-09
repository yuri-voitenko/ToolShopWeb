package com.epam.preprod.voitenko.servlet;

import com.epam.preprod.voitenko.bean.RegisterBean;
import com.epam.preprod.voitenko.repository.CaptchaRepository;
import com.epam.preprod.voitenko.strategy.CaptchaStrategy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static com.epam.preprod.voitenko.constant.Constatns.Message.HINT_CAPTCHA_CODE;
import static com.epam.preprod.voitenko.constant.Constatns.Message.HINT_NOT_EMPTY_FIELD;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class RegistrationUserTest {
    @Mock
    HttpServletRequest mockHttpServletRequest;
    @Mock
    HttpServletResponse mockHttpServletResponse;
    @Mock
    HttpSession mockHttpSession;
    @Mock
    RequestDispatcher mockRequestDispatcher;
    @Mock
    ServletContext mockServletContext;
    @Mock
    CaptchaStrategy captchaStrategy;

    RegistrationUser registrationUser;
    RegisterBean emptyRegisterBean;
    Map<String, String> allErrors;

    @Before
    public void setUp() throws ServletException, IOException {
        initMocks(this);
        registrationUser = new RegistrationUser();
        emptyRegisterBean = new RegisterBean();
        allErrors = new LinkedHashMap<>();
        allErrors.put(FULL_NAME, HINT_NOT_EMPTY_FIELD);
        allErrors.put(ADDRESS, HINT_NOT_EMPTY_FIELD);
        allErrors.put(PHONE_NUMBER, HINT_NOT_EMPTY_FIELD);
        allErrors.put(EMAIL, HINT_NOT_EMPTY_FIELD);
        allErrors.put(PASSWORD, HINT_NOT_EMPTY_FIELD);
        allErrors.put(PASSWORD_CHECK, HINT_NOT_EMPTY_FIELD);
        allErrors.put(CAPTCHA, HINT_CAPTCHA_CODE);
        when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
        when(mockHttpServletRequest.getRequestDispatcher(anyString())).thenReturn(mockRequestDispatcher);
        when(mockServletContext.getAttribute(STRATEGY)).thenReturn(captchaStrategy);
        when(mockServletContext.getInitParameter(TIMEOUT)).thenReturn("300");
        when(mockHttpServletRequest.getServletContext()).thenReturn(mockServletContext);
    }

    @Test
    public void doGetShouldForwardToViewRegisterServlet() throws ServletException, IOException {
        registrationUser.doGet(mockHttpServletRequest, mockHttpServletResponse);
        verify(mockHttpServletRequest.getRequestDispatcher("/viewRegisterForm"))
                .forward(mockHttpServletRequest, mockHttpServletResponse);
        when(mockHttpSession.getAttribute(anyString())).thenReturn(null);
        doNothing().when(mockHttpSession).removeAttribute(anyString());
        doNothing().when(mockHttpServletRequest).setAttribute(anyString(), anyObject());
    }

    @Test
    public void doPostShouldRedirectToRegistrationUserServletWhenAllFieldsIsEmpty() throws ServletException, IOException {
        when(captchaStrategy.getIdCaptcha(mockHttpServletRequest)).thenReturn(CaptchaRepository.addCaptcha());
        registrationUser.doPost(mockHttpServletRequest, mockHttpServletResponse);
        verify(mockHttpSession).setAttribute(REG_BEAN, emptyRegisterBean);
        verify(mockHttpSession).setAttribute(ERRORS, allErrors);
        verify(mockHttpServletResponse).sendRedirect("/registerUser");
    }
}