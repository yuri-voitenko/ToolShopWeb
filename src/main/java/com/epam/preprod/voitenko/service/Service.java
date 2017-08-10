package com.epam.preprod.voitenko.service;

import com.epam.preprod.voitenko.bean.RegisterBean;
import com.epam.preprod.voitenko.bean.UserBean;

import javax.servlet.http.HttpServletRequest;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;

public class Service {
    private Service() {
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