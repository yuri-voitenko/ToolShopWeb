package com.epam.preprod.voitenko.servlet.user;

import com.epam.preprod.voitenko.entity.LoginEntity;
import com.epam.preprod.voitenko.entity.UserEntity;
import com.epam.preprod.voitenko.handler.DataSourceHandler;
import com.epam.preprod.voitenko.service.UserService;
import com.epam.preprod.voitenko.servlet.user.LoginUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.epam.preprod.voitenko.constant.Constatns.EMPTY_STRING;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static com.epam.preprod.voitenko.constant.Constatns.Message.*;
import static com.epam.preprod.voitenko.servlet.user.LoginUser.NUMBER_OF_ATTEMPTS;
import static com.epam.preprod.voitenko.util.ServiceUtil.getHashPassword;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest({LoginUser.class, DataSourceHandler.class, UserService.class})
public class LoginUserTest {
    @Mock
    private HttpServletRequest mockHttpServletRequest;
    @Mock
    private HttpServletResponse mockHttpServletResponse;
    @Mock
    private HttpSession mockHttpSession;
    @Mock
    private RequestDispatcher mockRequestDispatcher;
    @Mock
    private DataSourceHandler mockDataSourceHandler;
    @Mock
    private DataSource mockDataSource;
    @Mock
    private UserService mockUserService;
    @Mock
    private Connection mockConnection;

    private LoginUser loginUser;
    private LoginEntity emptyLogEntity;
    private LoginEntity correctLogEntity;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        loginUser = new LoginUser();
        emptyLogEntity = new LoginEntity();
        correctLogEntity = new LoginEntity();
        correctLogEntity.setEmail("voit@gmail.com");
        correctLogEntity.setPassword("Voitenko!335");
        when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
        when(mockHttpServletRequest.getRequestDispatcher(anyString())).thenReturn(mockRequestDispatcher);
        mockStatic(DataSourceHandler.class);
        when(DataSourceHandler.getInstance()).thenReturn(mockDataSourceHandler);
        when(mockDataSourceHandler.getDataSource()).thenReturn(mockDataSource);
        whenNew(UserService.class).withAnyArguments().thenReturn(mockUserService);
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
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
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put(EMAIL, HINT_NOT_EMPTY_FIELD);
        errors.put(PASSWORD, HINT_NOT_EMPTY_FIELD);
        verify(mockHttpSession).setAttribute(LOGIN_ENTITY, emptyLogEntity);
        verify(mockHttpSession).setAttribute(ERRORS, errors);
        verify(mockHttpServletResponse).sendRedirect("/loginUser");
    }

    @Test
    public void doPostShouldRedirectToLoginUserServletWhenSuchEmailNotRegistered() throws ServletException, IOException {
        when(mockHttpServletRequest.getParameter(EMAIL)).thenReturn(correctLogEntity.getEmail());
        when(mockHttpServletRequest.getParameter(PASSWORD)).thenReturn(correctLogEntity.getPassword());
        when(mockUserService.getUserByEmail(anyString())).thenReturn(null);
        loginUser.doPost(mockHttpServletRequest, mockHttpServletResponse);

        correctLogEntity.setEmail(EMPTY_STRING);
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put(EMAIL, NOT_LOGIN_EMAIL);
        verify(mockHttpSession).setAttribute(LOGIN_ENTITY, correctLogEntity);
        verify(mockHttpSession).setAttribute(ERRORS, errors);
        verify(mockHttpServletResponse).sendRedirect("/loginUser");
    }

    @Test
    public void doPostShouldRedirectToHomePageServletWhenIncorrectPassword() throws ServletException, IOException {
        when(mockHttpServletRequest.getParameter(EMAIL)).thenReturn(correctLogEntity.getEmail());
        when(mockHttpServletRequest.getParameter(PASSWORD)).thenReturn(correctLogEntity.getPassword());
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(getHashPassword("Another password"));
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setEmail(correctLogEntity.getEmail());
        loginEntity.increaseCount();

        when(mockUserService.getUserByEmail(anyString())).thenReturn(userEntity);
        loginUser.doPost(mockHttpServletRequest, mockHttpServletResponse);

        String mes = NOT_LOGIN_PASSWORD + "<br>" + String.format(WARN_BAN, NUMBER_OF_ATTEMPTS - loginEntity.getFailCount());
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put(PASSWORD, mes);
        verify(mockHttpSession).setAttribute(LOGIN_ENTITY, loginEntity);
        verify(mockHttpSession).setAttribute(ERRORS, errors);
        verify(mockHttpServletResponse).sendRedirect("/loginUser");
    }

    @Test
    public void doPostShouldRedirectToHomePageServletWhenSuccessLogin() throws ServletException, IOException {
        when(mockHttpServletRequest.getParameter(EMAIL)).thenReturn(correctLogEntity.getEmail());
        when(mockHttpServletRequest.getParameter(PASSWORD)).thenReturn(correctLogEntity.getPassword());
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(getHashPassword(correctLogEntity.getPassword()));
        when(mockUserService.getUserByEmail(anyString())).thenReturn(userEntity);
        loginUser.doPost(mockHttpServletRequest, mockHttpServletResponse);

        verify(mockHttpSession).setAttribute(USER_ENTITY, userEntity);
        verify(mockHttpServletResponse).sendRedirect("/viewHomePage");
    }
}