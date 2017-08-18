package com.epam.preprod.voitenko.servlet;

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
import java.util.Map;

import static com.epam.preprod.voitenko.constant.Constatns.EMPTY_STRING;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static com.epam.preprod.voitenko.constant.Constatns.Message.NOT_LOGIN_EMAIL;
import static com.epam.preprod.voitenko.constant.Constatns.Message.NOT_LOGIN_PASSWORD;
import static com.epam.preprod.voitenko.util.ServiceUtil.getHashPassword;
import static com.epam.preprod.voitenko.util.ServiceUtil.removeSessionAttribute;

@WebServlet("/loginUser")
public class LoginUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        removeSessionAttribute(httpServletRequest, LOGIN_ENTITY);
        removeSessionAttribute(httpServletRequest, ERRORS);
        httpServletRequest.getRequestDispatcher("/viewLoginForm").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        LoginEntity loginEntity = ServiceUtil.extractLoginEntity(httpServletRequest);
        Map<String, String> errors = ValidatorUtil.validate(loginEntity);
        HttpSession session = httpServletRequest.getSession();

        if (!errors.isEmpty()) {
            session.setAttribute(LOGIN_ENTITY, loginEntity);
        } else {
            DataSource dataSource = DataSourceHandler.getInstance().getDataSource();
            UserService userService = new UserService(dataSource);
            UserEntity userEntity = userService.getUserByEmail(loginEntity.getEmail());
            if (userEntity == null) {
                errors.put(EMAIL, NOT_LOGIN_EMAIL);
                loginEntity.setEmail(EMPTY_STRING);
                session.setAttribute(LOGIN_ENTITY, loginEntity);
            } else {
                if (getHashPassword(loginEntity.getPassword()).equals(userEntity.getPassword())) {
                    session.setAttribute(USER_ENTITY, userEntity);
                } else {
                    errors.put(PASSWORD, NOT_LOGIN_PASSWORD);
                }
            }
        }
        session.setAttribute(ERRORS, errors);
        String redirect = errors.isEmpty() ? "/viewHomePage" : "/loginUser";
        httpServletResponse.sendRedirect(redirect);
    }
}