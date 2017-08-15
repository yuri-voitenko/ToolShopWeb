package com.epam.preprod.voitenko.servlet;

import com.epam.preprod.voitenko.bean.LoginBean;
import com.epam.preprod.voitenko.bean.UserBean;
import com.epam.preprod.voitenko.service.Service;
import com.epam.preprod.voitenko.service.UserService;
import com.epam.preprod.voitenko.validate.ValidatorUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static com.epam.preprod.voitenko.constant.Constatns.Message.NOT_LOGIN_EMAIL;
import static com.epam.preprod.voitenko.constant.Constatns.Message.NOT_LOGIN_PASSWORD;
import static com.epam.preprod.voitenko.service.Service.getHashPassword;
import static com.epam.preprod.voitenko.service.Service.removeSessionAttribute;

@WebServlet("/loginUser")
public class LoginUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        removeSessionAttribute(httpServletRequest, LOGIN_BEAN);
        removeSessionAttribute(httpServletRequest, ERRORS);
        httpServletRequest.getRequestDispatcher("/viewLoginForm").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        LoginBean loginBean = Service.extractLoginBean(httpServletRequest);
        Map<String, String> errors = ValidatorUtil.validate(loginBean);
        HttpSession session = httpServletRequest.getSession();

        if (!errors.isEmpty()) {
            session.setAttribute(LOGIN_BEAN, loginBean);
        } else {
            UserService userService = new UserService();
            UserBean userBean = userService.getUserByEmail(loginBean.getEmail());
            if (userBean == null) {
                errors.put(EMAIL, NOT_LOGIN_EMAIL);
                loginBean.setEmail("");
                session.setAttribute(LOGIN_BEAN, loginBean);
            } else {
                if (getHashPassword(loginBean.getPassword()).equals(userBean.getPassword())) {
                    session.setAttribute(USER_BEAN, userBean);
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