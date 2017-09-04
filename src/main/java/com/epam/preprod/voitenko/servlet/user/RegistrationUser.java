package com.epam.preprod.voitenko.servlet.user;

import com.epam.preprod.voitenko.entity.RegisterEntity;
import com.epam.preprod.voitenko.entity.UserEntity;
import com.epam.preprod.voitenko.handler.DataSourceHandler;
import com.epam.preprod.voitenko.service.UserService;
import com.epam.preprod.voitenko.strategy.captcha.CaptchaStrategy;
import com.epam.preprod.voitenko.util.ServiceUtil;
import com.epam.preprod.voitenko.util.ValidatorUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Map;

import static com.epam.preprod.voitenko.constant.Constatns.EMPTY_STRING;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.CAPTCHA;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.EMAIL;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.ERRORS;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.FAIL_REGISTRATION;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.REG_ENTITY;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.STRATEGY;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.SUCCESS_REGISTRATION;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.TIMEOUT;
import static com.epam.preprod.voitenko.constant.Constatns.Message.HINT_FAIL_REGISTRATION;
import static com.epam.preprod.voitenko.constant.Constatns.Message.HINT_SAME_EMAIl;
import static com.epam.preprod.voitenko.constant.Constatns.Message.HINT_SUCCESS_REGISTRATION;
import static com.epam.preprod.voitenko.util.ServiceUtil.removeSessionAttributeAndSetRequestAttribute;

@WebServlet("/registerUser")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class RegistrationUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        removeSessionAttributeAndSetRequestAttribute(req, REG_ENTITY);
        removeSessionAttributeAndSetRequestAttribute(req, ERRORS);
        removeSessionAttributeAndSetRequestAttribute(req, SUCCESS_REGISTRATION);
        req.getRequestDispatcher("/viewRegisterForm").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegisterEntity regBean = ServiceUtil.extractRegisterEntity(req);
        Map<String, String> errors = ValidatorUtil.validate(regBean);
        HttpSession session = req.getSession();
        validateCaptcha(req);

        if (isFormCorrectlyFilled(regBean, errors, session)) {
            tryRegisterNewUser(regBean, errors, session);
        }
        session.setAttribute(ERRORS, errors);
        resp.sendRedirect("/registerUser");
    }

    private void validateCaptcha(HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        String codeCaptcha = request.getParameter(CAPTCHA);
        CaptchaStrategy strategy = (CaptchaStrategy) servletContext.getAttribute(STRATEGY);
        int idCaptcha = strategy.getIdCaptcha(request);
        long timeout = Long.parseLong(servletContext.getInitParameter(TIMEOUT));
        ValidatorUtil.validateCaptcha(idCaptcha, codeCaptcha, timeout);
    }

    private boolean isFormCorrectlyFilled(RegisterEntity regBean, Map<String, String> errors, HttpSession session) {
        if (!errors.isEmpty()) {
            session.setAttribute(REG_ENTITY, regBean);
            return false;
        }
        return true;
    }

    private void tryRegisterNewUser(RegisterEntity regBean, Map<String, String> errors, HttpSession session) {
        UserEntity user = ServiceUtil.fillUserEntity(regBean);
        UserService userService = getUserService();
        if (!isAlreadyRegisteredUserWithSuchEmail(regBean, errors, session, user, userService)) {
            registerNewUser(errors, session, user, userService);
        }
    }

    private UserService getUserService() {
        DataSource dataSource = DataSourceHandler.getInstance().getDataSource();
        return new UserService(dataSource);
    }

    private boolean isAlreadyRegisteredUserWithSuchEmail(RegisterEntity regBean, Map<String, String> errors, HttpSession session, UserEntity user, UserService userService) {
        if (userService.getUserByEmail(user.getEmail()) != null) {
            regBean.setEmail(EMPTY_STRING);
            session.setAttribute(REG_ENTITY, regBean);
            errors.put(EMAIL, HINT_SAME_EMAIl);
            return true;
        }
        return false;
    }

    private void registerNewUser(Map<String, String> errors, HttpSession session, UserEntity user, UserService userService) {
        if (userService.registerUser(user)) {
            session.setAttribute(SUCCESS_REGISTRATION, HINT_SUCCESS_REGISTRATION);
        } else {
            errors.put(FAIL_REGISTRATION, HINT_FAIL_REGISTRATION);
        }
    }
}