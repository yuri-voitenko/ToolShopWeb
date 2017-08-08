package com.epam.preprod.voitenko.service;

import com.epam.preprod.voitenko.bean.RegisterBean;
import com.epam.preprod.voitenko.bean.UserBean;

import javax.servlet.http.HttpServletRequest;

public class Service {
    private Service() {
    }

    public static RegisterBean extractRegisterBean(HttpServletRequest httpServletRequest) {
        RegisterBean regBean = new RegisterBean();
        regBean.setFullName(httpServletRequest.getParameter("fullName"));
        regBean.setAddress(httpServletRequest.getParameter("address"));
        regBean.setPhoneNumber(httpServletRequest.getParameter("phoneNumber"));
        regBean.setEmail(httpServletRequest.getParameter("email"));
        regBean.setPassword(httpServletRequest.getParameter("password"));
        regBean.setRepeatedPassword(httpServletRequest.getParameter("passwordCheck"));

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