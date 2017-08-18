package com.epam.preprod.voitenko.servlet;

import com.epam.preprod.voitenko.entity.RegisterEntity;
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
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static com.epam.preprod.voitenko.constant.Constatns.Message.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class RegistrationUserTest {
    @Mock
    private HttpServletRequest mockHttpServletRequest;
    @Mock
    private HttpServletResponse mockHttpServletResponse;
    @Mock
    private HttpSession mockHttpSession;
    @Mock
    private RequestDispatcher mockRequestDispatcher;
    @Mock
    private ServletContext mockServletContext;
    @Mock
    private CaptchaStrategy captchaStrategy;
    @Mock
    private Part mockPart;

    private RegistrationUser registrationUser;
    private RegisterEntity emptyRegBean;
    private RegisterEntity correctRegBean;
    private Map<String, String> allErrors;

    @Before
    public void setUp() throws ServletException, IOException {
        initMocks(this);
        registrationUser = new RegistrationUser();
        emptyRegBean = new RegisterEntity();
        correctRegBean = new RegisterEntity();
        correctRegBean.setFullName("Yuri Voitenko");
        correctRegBean.setAddress("Kharkiv");
        correctRegBean.setPhoneNumber("+380505730182");
        correctRegBean.setEmail("goodemail@ukr.net");
        correctRegBean.setPassword("Qwerty!123");
        correctRegBean.setRepeatedPassword("Qwerty!123");
        allErrors = new LinkedHashMap<>();
        allErrors.put(FULL_NAME, HINT_NOT_EMPTY_FIELD);
        allErrors.put(ADDRESS, HINT_NOT_EMPTY_FIELD);
        allErrors.put(PHONE_NUMBER, HINT_NOT_EMPTY_FIELD);
        allErrors.put(EMAIL, HINT_NOT_EMPTY_FIELD);
        allErrors.put(PASSWORD, HINT_NOT_EMPTY_FIELD);
        allErrors.put(PASSWORD_CHECK, HINT_NOT_EMPTY_FIELD);
        allErrors.put(CAPTCHA, HINT_CAPTCHA_CODE);
        when(mockPart.getHeader("Content-Disposition")).thenReturn("");
        when(mockHttpServletRequest.getPart(AVATAR)).thenReturn(mockPart);
        when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
        when(mockHttpServletRequest.getRequestDispatcher(anyString())).thenReturn(mockRequestDispatcher);
        when(mockServletContext.getAttribute(STRATEGY)).thenReturn(captchaStrategy);
        when(mockServletContext.getInitParameter(TIMEOUT)).thenReturn("300");
        when(mockHttpServletRequest.getServletContext()).thenReturn(mockServletContext);
        when(captchaStrategy.getIdCaptcha(mockHttpServletRequest)).thenReturn(CaptchaRepository.addCaptcha());
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
        verifyRegBeanAndErrors();
        verify(mockHttpServletResponse).sendRedirect("/registerUser");
    }

    @Test
    public void doPostShouldRedirectToRegistrationUserServletWhenIncorrectFullName() throws ServletException, IOException {
        when(mockHttpServletRequest.getParameter(FULL_NAME)).thenReturn("not_correct");
        allErrors.put(FULL_NAME, HINT_FULL_NAME);
        verifyRegBeanAndErrors();
    }

    @Test
    public void doPostShouldRedirectToRegistrationUserServletWhenIncorrectPhoneNumber() throws ServletException, IOException {
        when(mockHttpServletRequest.getParameter(PHONE_NUMBER)).thenReturn("not_correct");
        allErrors.put(PHONE_NUMBER, HINT_PHONE_NUMBER);
        verifyRegBeanAndErrors();
    }

    @Test
    public void doPostShouldRedirectToRegistrationUserServletWhenIncorrectEmail() throws ServletException, IOException {
        when(mockHttpServletRequest.getParameter(EMAIL)).thenReturn("not_correct");
        allErrors.put(EMAIL, HINT_EMAIL);
        verifyRegBeanAndErrors();
    }

    @Test
    public void doPostShouldRedirectToRegistrationUserServletWhenIncorrectPassword() throws ServletException, IOException {
        when(mockHttpServletRequest.getParameter(PASSWORD)).thenReturn("not_correct");
        allErrors.put(PASSWORD, HINT_PASSWORD);
        verifyRegBeanAndErrors();
    }

    @Test
    public void doPostShouldRedirectToRegistrationUserServletWhenIncorrectRepeatedPassword() throws ServletException, IOException {
        when(mockHttpServletRequest.getParameter(PASSWORD)).thenReturn("Qwerty!123");
        when(mockHttpServletRequest.getParameter(PASSWORD_CHECK)).thenReturn("qwerty!123");
        emptyRegBean.setPassword("Qwerty!123");
        allErrors.remove(PASSWORD);
        allErrors.put(PASSWORD_CHECK, HINT_PASSWORD_CHECK);
        verifyRegBeanAndErrors();
    }

    @Test
    public void doPostShouldRedirectToRegistrationUserServletWhenIncorrectIdCaptcha() throws ServletException, IOException {
        when(captchaStrategy.getIdCaptcha(mockHttpServletRequest)).thenReturn(-1);
        allErrors.put(CAPTCHA, HINT_CAPTCHA_LIFETIME);
        verifyRegBeanAndErrors();
    }

    private void verifyRegBeanAndErrors() throws ServletException, IOException {
        registrationUser.doPost(mockHttpServletRequest, mockHttpServletResponse);
        verify(mockHttpSession).setAttribute(REG_ENTITY, emptyRegBean);
        verify(mockHttpSession).setAttribute(ERRORS, allErrors);
    }
}