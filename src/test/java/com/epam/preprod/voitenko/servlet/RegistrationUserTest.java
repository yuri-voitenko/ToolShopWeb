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
import static com.epam.preprod.voitenko.constant.Constatns.Message.*;
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
    RegisterBean emptyRegBean;
    RegisterBean correctRegBean;
    Map<String, String> allErrors;

    @Before
    public void setUp() throws ServletException, IOException {
        initMocks(this);
        registrationUser = new RegistrationUser();
        emptyRegBean = new RegisterBean();
        correctRegBean = new RegisterBean();
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

    @Test
    public void doPostShouldRedirectToRegistrationUserServletWhenSuccess() throws ServletException, IOException {
//        when(mockHttpServletRequest.getParameter(FULL_NAME)).thenReturn(correctRegBean.getFullName());
//        when(mockHttpServletRequest.getParameter(ADDRESS)).thenReturn(correctRegBean.getAddress());
//        when(mockHttpServletRequest.getParameter(PHONE_NUMBER)).thenReturn(correctRegBean.getPhoneNumber());
//        when(mockHttpServletRequest.getParameter(EMAIL)).thenReturn(correctRegBean.getEmail());
//        when(mockHttpServletRequest.getParameter(PASSWORD)).thenReturn(correctRegBean.getPassword());
//        when(mockHttpServletRequest.getParameter(PASSWORD_CHECK)).thenReturn(correctRegBean.getRepeatedPassword());
//        int idCaptcha = CaptchaRepository.addCaptcha();
//        Captcha objCaptcha = CaptchaRepository.getCaptcha(idCaptcha);
//        when(captchaStrategy.getIdCaptcha(mockHttpServletRequest)).thenReturn(idCaptcha);
//        when(mockHttpServletRequest.getParameter(CAPTCHA)).thenReturn(String.valueOf(objCaptcha.getSecretCode()));
//        allErrors.clear();
//        registrationUser.doPost(mockHttpServletRequest, mockHttpServletResponse);
//        verify(mockHttpSession).setAttribute(SUCCESS_REGISTRATION, HINT_SUCCESS_REGISTRATION);
    }

    @Test
    public void doPostShouldRedirectToRegistrationUserServletWhenSameEmail() throws ServletException, IOException {
//        correctRegBean.setEmail("voit@gmail.com");
//        when(mockHttpServletRequest.getParameter(FULL_NAME)).thenReturn(correctRegBean.getFullName());
//        when(mockHttpServletRequest.getParameter(ADDRESS)).thenReturn(correctRegBean.getAddress());
//        when(mockHttpServletRequest.getParameter(PHONE_NUMBER)).thenReturn(correctRegBean.getPhoneNumber());
//        when(mockHttpServletRequest.getParameter(EMAIL)).thenReturn(correctRegBean.getEmail());
//        when(mockHttpServletRequest.getParameter(PASSWORD)).thenReturn(correctRegBean.getPassword());
//        when(mockHttpServletRequest.getParameter(PASSWORD_CHECK)).thenReturn(correctRegBean.getRepeatedPassword());
//        int idCaptcha = CaptchaRepository.addCaptcha();
//        Captcha objCaptcha = CaptchaRepository.getCaptcha(idCaptcha);
//        when(captchaStrategy.getIdCaptcha(mockHttpServletRequest)).thenReturn(idCaptcha);
//        when(mockHttpServletRequest.getParameter(CAPTCHA)).thenReturn(String.valueOf(objCaptcha.getSecretCode()));
//        allErrors.clear();
//        allErrors.put(EMAIL, HINT_SAME_EMAIl);
//        correctRegBean.setEmail("");
//        emptyRegBean = correctRegBean;
//        verifyRegBeanAndErrors();
    }

    private void verifyRegBeanAndErrors() throws ServletException, IOException {
        registrationUser.doPost(mockHttpServletRequest, mockHttpServletResponse);
        verify(mockHttpSession).setAttribute(REG_BEAN, emptyRegBean);
        verify(mockHttpSession).setAttribute(ERRORS, allErrors);
    }
}