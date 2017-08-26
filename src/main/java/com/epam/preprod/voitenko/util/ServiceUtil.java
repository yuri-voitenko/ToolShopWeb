package com.epam.preprod.voitenko.util;

import com.epam.preprod.voitenko.entity.*;
import com.epam.preprod.voitenko.sqlbuilder.SQLBuilder;
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
import static com.epam.preprod.voitenko.util.ValidatorUtil.isNullOrEmpty;

public class ServiceUtil {
    private static final Logger LOGGER = LogManager.getLogger(ServiceUtil.class);

    private ServiceUtil() {
    }

    public static String getHashPassword(String password) {
        return DigestUtils.md5Hex(password);
    }

    public static void removeSessionAttributeAndSetRequestAttribute(HttpServletRequest httpServletRequest, String key) {
        HttpSession session = httpServletRequest.getSession();
        Object object = session.getAttribute(key);
        if (object != null) {
            httpServletRequest.setAttribute(key, object);
            session.removeAttribute(key);
        }
    }

    public static FilterEntity extractFilterEntity(HttpServletRequest httpServletRequest) {
        FilterEntity filterEntity = new FilterEntity();
        filterEntity.setNameTool(httpServletRequest.getParameter(NAME_TOOL));
        filterEntity.setCategory(httpServletRequest.getParameter(CATEGORY));
        filterEntity.setManufacturers(httpServletRequest.getParameterValues(MANUFACTURER));
        filterEntity.setLowPrice(httpServletRequest.getParameter(LOW_PRICE));
        filterEntity.setHighPrice(httpServletRequest.getParameter(HIGH_PRICE));

        String orderKey = httpServletRequest.getParameter(ORDER_KEY);
        if (orderKey != null) {
            filterEntity.setOrderKey(orderKey);
        }
        String orderDirection = httpServletRequest.getParameter(ORDER_DIRECTION);
        if (orderDirection != null) {
            filterEntity.setOrderDirection(orderDirection);
        }
        String numberToolsOnPage = httpServletRequest.getParameter(NUMBER_TOOLS_ON_PAGE);
        if (numberToolsOnPage != null) {
            filterEntity.setNumberToolsOnPage(numberToolsOnPage);
        }
        String numberPage = httpServletRequest.getParameter(NUMBER_PAGE);
        if (numberPage != null) {
            filterEntity.setNumberPage(numberPage);
        }
        return filterEntity;
    }

    public static LoginEntity extractLoginEntity(HttpServletRequest httpServletRequest) {
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setEmail(httpServletRequest.getParameter(EMAIL));
        loginEntity.setPassword(httpServletRequest.getParameter(PASSWORD));
        return loginEntity;
    }

    public static RegisterEntity extractRegisterEntity(HttpServletRequest httpServletRequest) {
        RegisterEntity registerEntity = new RegisterEntity();
        registerEntity.setFullName(httpServletRequest.getParameter(FULL_NAME));
        registerEntity.setAddress(httpServletRequest.getParameter(ADDRESS));
        registerEntity.setPhoneNumber(httpServletRequest.getParameter(PHONE_NUMBER));
        registerEntity.setEmail(httpServletRequest.getParameter(EMAIL));
        registerEntity.setPassword(httpServletRequest.getParameter(PASSWORD));
        registerEntity.setRepeatedPassword(httpServletRequest.getParameter(PASSWORD_CHECK));
        uploadAvatar(httpServletRequest, registerEntity);
        return registerEntity;
    }

    public static String createSQL(FilterEntity filterEntity) {
        SQLBuilder sqlBuilder = new SQLBuilder();
        if (!isNullOrEmpty(filterEntity.getNameTool())) {
            sqlBuilder.where(NAME + " LIKE '%" + filterEntity.getNameTool() + "%'");
        }
        if (!isNullOrEmpty(filterEntity.getCategory())) {
            sqlBuilder.where(CATEGORY + " LIKE '" + filterEntity.getCategory() + "'");
        }
        if (!isNullOrEmpty(filterEntity.getManufacturers())) {
            StringBuilder values = new StringBuilder();
            for (String manufacturer : filterEntity.getManufacturers()) {
                values.append('\'');
                values.append(manufacturer);
                values.append("', ");
            }
            values.delete(values.length() - 2, values.length());
            sqlBuilder.where(MANUFACTURER + " IN (" + values + ")");
        }
        if (!isNullOrEmpty(filterEntity.getLowPrice())) {
            sqlBuilder.where(COST + " >= " + filterEntity.getLowPrice());
        }
        if (!isNullOrEmpty(filterEntity.getHighPrice())) {
            sqlBuilder.where(COST + " <= " + filterEntity.getHighPrice());
        }
        sqlBuilder.orderBy(filterEntity.getOrderKey(), filterEntity.getOrderDirection());
        sqlBuilder.limit(filterEntity.getNumberPage(), filterEntity.getNumberToolsOnPage());
        return sqlBuilder.toString();
    }

    public static UserEntity fillUserEntity(RegisterEntity registerEntity) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(registerEntity.getFullName());
        userEntity.setAddress(registerEntity.getAddress());
        userEntity.setPhoneNumber(registerEntity.getPhoneNumber());
        userEntity.setEmail(registerEntity.getEmail());
        userEntity.setPassword(registerEntity.getPassword());
        userEntity.setAvatar(registerEntity.getAvatar());
        return userEntity;
    }

    public static Cart<ElectricToolEntity> getCart(HttpSession session) {
        Cart<ElectricToolEntity> cart = (CartEntity) session.getAttribute(CART);
        if (cart == null) {
            cart = new CartEntity();
        }
        return cart;
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
                } while (Paths.get(fullPath).toFile().exists());
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