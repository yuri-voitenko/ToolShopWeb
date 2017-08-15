package com.epam.preprod.voitenko.service;

import com.epam.preprod.voitenko.bean.LoginBean;
import com.epam.preprod.voitenko.bean.RegisterBean;
import com.epam.preprod.voitenko.bean.UserBean;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;

public class Service {
    private Service() {
    }

    public static String getHashPassword(String password) {
        return DigestUtils.md5Hex(password);
    }

    public static void removeSessionAttribute(HttpServletRequest httpServletRequest, String key) {
        HttpSession session = httpServletRequest.getSession();
        Object object = session.getAttribute(key);
        if (object != null) {
            httpServletRequest.setAttribute(key, object);
            session.removeAttribute(key);
        }
    }

    public static LoginBean extractLoginBean(HttpServletRequest httpServletRequest) {
        LoginBean loginBean = new LoginBean();
        loginBean.setEmail(httpServletRequest.getParameter(EMAIL));
        loginBean.setPassword(httpServletRequest.getParameter(PASSWORD));
        return loginBean;
    }

    public static RegisterBean extractRegisterBean(HttpServletRequest httpServletRequest) {
        RegisterBean regBean = new RegisterBean();
        regBean.setFullName(httpServletRequest.getParameter(FULL_NAME));
        regBean.setAddress(httpServletRequest.getParameter(ADDRESS));
        regBean.setPhoneNumber(httpServletRequest.getParameter(PHONE_NUMBER));
        regBean.setEmail(httpServletRequest.getParameter(EMAIL));
        regBean.setPassword(httpServletRequest.getParameter(PASSWORD));
        regBean.setRepeatedPassword(httpServletRequest.getParameter(PASSWORD_CHECK));
        return regBean;
    }

    public static UserBean fillUserBean(RegisterBean registerBean) {
        UserBean userBean = new UserBean();
        userBean.setFullName(registerBean.getFullName());
        userBean.setAddress(registerBean.getAddress());
        userBean.setPhoneNumber(registerBean.getPhoneNumber());
        userBean.setEmail(registerBean.getEmail());
        userBean.setPassword(registerBean.getPassword());
        return userBean;
    }
}