package com.epam.preprod.voitenko.servlet;

import com.epam.preprod.voitenko.entity.RegisterEntity;
import com.epam.preprod.voitenko.entity.UserEntity;
import com.epam.preprod.voitenko.handler.DataSourceHandler;
import com.epam.preprod.voitenko.service.UserService;
import com.epam.preprod.voitenko.strategy.CaptchaStrategy;
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
import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static com.epam.preprod.voitenko.constant.Constatns.Message.*;
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

        if (!errors.isEmpty()) {
            session.setAttribute(REG_ENTITY, regBean);
        } else {
            UserEntity user = ServiceUtil.fillUserEntity(regBean);
            DataSource dataSource = DataSourceHandler.getInstance().getDataSource();
            UserService userService = new UserService(dataSource);
            if (userService.getUserByEmail(user.getEmail()) != null) {
                regBean.setEmail(EMPTY_STRING);
                session.setAttribute(REG_ENTITY, regBean);
                errors.put(EMAIL, HINT_SAME_EMAIl);
            } else {
                if (userService.registerUser(user)) {
                    session.setAttribute(SUCCESS_REGISTRATION, HINT_SUCCESS_REGISTRATION);
                } else {
                    errors.put(FAIL_REGISTRATION, HINT_FAIL_REGISTRATION);
                }
            }
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
}