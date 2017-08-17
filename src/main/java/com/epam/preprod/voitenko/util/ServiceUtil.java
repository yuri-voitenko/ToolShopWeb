package com.epam.preprod.voitenko.util;

import com.epam.preprod.voitenko.entity.LoginEntity;
import com.epam.preprod.voitenko.entity.RegisterEntity;
import com.epam.preprod.voitenko.entity.UserEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static com.epam.preprod.voitenko.constant.Constatns.PATH_TO_AVATARS;
import static com.epam.preprod.voitenko.constant.Constatns.RegEx.*;
import static java.nio.file.Files.exists;

public class ServiceUtil {
    private static final Logger LOGGER = LogManager.getLogger(ServiceUtil.class);

    private ServiceUtil() {
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

    public static LoginEntity extractLoginBean(HttpServletRequest httpServletRequest) {
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setEmail(httpServletRequest.getParameter(EMAIL));
        loginEntity.setPassword(httpServletRequest.getParameter(PASSWORD));
        return loginEntity;
    }

    public static RegisterEntity extractRegisterBean(HttpServletRequest httpServletRequest) {
        RegisterEntity regBean = new RegisterEntity();
        regBean.setFullName(httpServletRequest.getParameter(FULL_NAME));
        regBean.setAddress(httpServletRequest.getParameter(ADDRESS));
        regBean.setPhoneNumber(httpServletRequest.getParameter(PHONE_NUMBER));
        regBean.setEmail(httpServletRequest.getParameter(EMAIL));
        regBean.setPassword(httpServletRequest.getParameter(PASSWORD));
        regBean.setRepeatedPassword(httpServletRequest.getParameter(PASSWORD_CHECK));
        uploadAvatar(httpServletRequest, regBean);
        return regBean;
    }

    public static UserEntity fillUserBean(RegisterEntity registerEntity) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(registerEntity.getFullName());
        userEntity.setAddress(registerEntity.getAddress());
        userEntity.setPhoneNumber(registerEntity.getPhoneNumber());
        userEntity.setEmail(registerEntity.getEmail());
        userEntity.setPassword(registerEntity.getPassword());
        userEntity.setAvatar(registerEntity.getAvatar());
        return userEntity;
    }

    private static void uploadAvatar(HttpServletRequest httpServletRequest, RegisterEntity regBean) {
        try {
            Part part = httpServletRequest.getPart(AVATAR);
            String disposition = part.getHeader("Content-Disposition");
            String fileName = disposition.replaceFirst(REGEX_FOR_PARSE_FILE_NAME, "$1");
            if (fileName.matches(REGEX_FILE_NAME_IMAGE)) {
                String fullPath;
                do {
                    String newFileName = "avatar_" + UUID.randomUUID() + "$2$3";
                    fileName = fileName.replaceFirst(REGEX_REPLACE_FILE_NAME_IMAGE, newFileName);
                    fullPath = System.getProperty("user.dir") + PATH_TO_AVATARS + fileName;
                } while (exists(Paths.get(fullPath)));
                regBean.setAvatar(fileName);
                part.write(fullPath);
            }
        } catch (IOException e) {
            LOGGER.error("IOException has occurred when try parse Part", e);
        } catch (ServletException e) {
            LOGGER.error("ServletException has occurred when try parse Part", e);
        }
    }
}