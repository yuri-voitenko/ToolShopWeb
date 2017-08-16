package com.epam.preprod.voitenko.service;

import com.epam.preprod.voitenko.bean.LoginBean;
import com.epam.preprod.voitenko.bean.RegisterBean;
import com.epam.preprod.voitenko.bean.UserBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static com.epam.preprod.voitenko.constant.Constatns.PATH_TO_AVATARS;

public class Service {
    private static final Logger LOGGER = LogManager.getLogger(Service.class);

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
        try {
            Part part = httpServletRequest.getPart(AVATAR);
            String disposition = part.getHeader("Content-Disposition");
            String fileName = disposition.replaceFirst("(?i)^.*filename=\"([^\"]+)\".*$", "$1");
            if (fileName.matches("(?i)(.*/)*.+\\.(png|jpg|jpeg)$")) {
                String fullPath = System.getProperty("user.dir") + PATH_TO_AVATARS + fileName;
                regBean.setAvatar(fileName);
                part.write(fullPath);
            }
        } catch (IOException e) {
            LOGGER.error("IOException has occurred when try parse Part", e);
        } catch (ServletException e) {
            LOGGER.error("ServletException has occurred when try parse Part", e);
        }
        return regBean;
    }

    public static UserBean fillUserBean(RegisterBean registerBean) {
        UserBean userBean = new UserBean();
        userBean.setFullName(registerBean.getFullName());
        userBean.setAddress(registerBean.getAddress());
        userBean.setPhoneNumber(registerBean.getPhoneNumber());
        userBean.setEmail(registerBean.getEmail());
        userBean.setPassword(registerBean.getPassword());
        userBean.setAvatar(registerBean.getAvatar());
        return userBean;
    }
}