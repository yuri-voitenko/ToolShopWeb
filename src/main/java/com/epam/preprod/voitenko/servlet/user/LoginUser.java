package com.epam.preprod.voitenko.servlet.user;

import com.epam.preprod.voitenko.entity.LoginEntity;
import com.epam.preprod.voitenko.entity.UserEntity;
import com.epam.preprod.voitenko.handler.DataSourceHandler;
import com.epam.preprod.voitenko.service.UserService;
import com.epam.preprod.voitenko.util.ServiceUtil;
import com.epam.preprod.voitenko.util.ValidatorUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

import static com.epam.preprod.voitenko.constant.Constatns.EMPTY_STRING;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.BAN;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.EMAIL;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.ERRORS;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.LOGIN_ENTITY;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.PASSWORD;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.USER_ENTITY;
import static com.epam.preprod.voitenko.constant.Constatns.Message.BAN_ACCOUNT;
import static com.epam.preprod.voitenko.constant.Constatns.Message.NOT_LOGIN_EMAIL;
import static com.epam.preprod.voitenko.constant.Constatns.Message.NOT_LOGIN_PASSWORD;
import static com.epam.preprod.voitenko.constant.Constatns.Message.WARN_BAN;
import static com.epam.preprod.voitenko.util.ServiceUtil.getHashPassword;
import static com.epam.preprod.voitenko.util.ServiceUtil.removeSessionAttributeAndSetRequestAttribute;

@WebServlet("/loginUser")
public class LoginUser extends HttpServlet {
    private final int NUMBER_OF_ATTEMPTS = 5;
    private final int BAN_TIME_IN_MINUTES = 2;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        removeSessionAttributeAndSetRequestAttribute(req, LOGIN_ENTITY);
        removeSessionAttributeAndSetRequestAttribute(req, ERRORS);
        req.getRequestDispatcher("/viewLoginForm").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        LoginEntity loginEntity = ServiceUtil.extractLoginEntity(req);
        Map<String, String> errors = ValidatorUtil.validate(loginEntity);

        verifyUser(session, loginEntity, errors);
        setLoginEntityAndErrorsToSessionAttributes(session, loginEntity, errors);
        String redirect = errors.isEmpty() ? "/viewHomePage" : "/loginUser";
        resp.sendRedirect(redirect);
    }

    private void verifyUser(HttpSession session, LoginEntity loginEntity, Map<String, String> errors) {
        if (errors.isEmpty()) {
            UserService userService = getUserService();
            UserEntity userEntity = userService.getUserByEmail(loginEntity.getEmail());
            failedPasswordInput(loginEntity, errors, session, userService, userEntity);
        }
    }

    private UserService getUserService() {
        DataSource dataSource = DataSourceHandler.getInstance().getDataSource();
        return new UserService(dataSource);
    }

    private void failedPasswordInput(LoginEntity loginEntity, Map<String, String> errors, HttpSession session, UserService userService, UserEntity userEntity) {
        if (isRegisteredUser(loginEntity, errors, userEntity)
                && !isBannedUser(errors, userEntity)
                && !isCorrectPassword(loginEntity, session, userEntity)) {
            loginEntity.increaseCount();
            setWarnBanMessageIfNeed(loginEntity, errors, userService, userEntity);
        }
    }

    private boolean isRegisteredUser(LoginEntity loginEntity, Map<String, String> errors, UserEntity userEntity) {
        if (userEntity == null) {
            errors.put(EMAIL, NOT_LOGIN_EMAIL);
            loginEntity.setEmail(EMPTY_STRING);
            return false;
        }
        return true;
    }

    private boolean isBannedUser(Map<String, String> errors, UserEntity userEntity) {
        Timestamp banExpirationDate = userEntity.getBanExpirationDate();
        if (banExpirationDate != null && banExpirationDate.after(getCurrentTimestamp())) {
            errors.put(BAN, BAN_ACCOUNT + banExpirationDate);
            return true;
        }
        return false;
    }

    private boolean isCorrectPassword(LoginEntity loginEntity, HttpSession session, UserEntity userEntity) {
        if (getHashPassword(loginEntity.getPassword()).equals(userEntity.getPassword())) {
            session.setAttribute(USER_ENTITY, userEntity);
            return true;
        }
        loginEntity.setPassword(EMPTY_STRING);
        return false;
    }

    private void setWarnBanMessageIfNeed(LoginEntity loginEntity, Map<String, String> errors, UserService userService, UserEntity userEntity) {
        if (!isLoginAttemptsExhausted(loginEntity, errors, userService, userEntity)) {
            String mes = NOT_LOGIN_PASSWORD + "<br>" + String.format(WARN_BAN, NUMBER_OF_ATTEMPTS - loginEntity.getFailCount());
            errors.put(PASSWORD, mes);
        }
    }

    private boolean isLoginAttemptsExhausted(LoginEntity loginEntity, Map<String, String> errors, UserService userService, UserEntity userEntity) {
        if (loginEntity.getFailCount() == NUMBER_OF_ATTEMPTS) {
            loginEntity.resetCount();
            userEntity.setBanExpirationDate(getBanExpirationDate(BAN_TIME_IN_MINUTES));
            errors.put(BAN, BAN_ACCOUNT + userEntity.getBanExpirationDate());
            userService.updateUser(userEntity);
            return true;
        }
        return false;
    }

    private void setLoginEntityAndErrorsToSessionAttributes(HttpSession session, LoginEntity loginEntity, Map<String, String> errors) {
        session.setAttribute(LOGIN_ENTITY, loginEntity);
        session.setAttribute(ERRORS, errors);
    }

    private Timestamp getBanExpirationDate(int minutes) {
        long offset = minutes * 60 * 1000;
        return new Timestamp(System.currentTimeMillis() + offset);
    }

    private Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}