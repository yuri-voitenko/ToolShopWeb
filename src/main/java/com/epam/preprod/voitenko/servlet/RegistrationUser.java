package com.epam.preprod.voitenko.servlet;

import com.epam.preprod.voitenko.bean.RegisterBean;
import com.epam.preprod.voitenko.bean.UserBean;
import com.epam.preprod.voitenko.repository.UserRepository;
import com.epam.preprod.voitenko.service.Service;
import com.epam.preprod.voitenko.strategy.CaptchaStrategy;
import com.epam.preprod.voitenko.validate.ValidatorUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static com.epam.preprod.voitenko.constant.Constatns.Message.HINT_SAME_EMAIl;

@WebServlet("/registerUser")
public class RegistrationUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        removeSessionAttribute(httpServletRequest, REG_BEAN);
        removeSessionAttribute(httpServletRequest, ERRORS);
        removeSessionAttribute(httpServletRequest, SUCCESS_REGISTRATION);
        httpServletRequest.getRequestDispatcher("/viewRegisterForm").forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        RegisterBean regBean = Service.extractRegisterBean(httpServletRequest);
        Map<String, String> errors = ValidatorUtil.validate(regBean);
        HttpSession session = httpServletRequest.getSession();
        validateCaptcha(httpServletRequest);

        if (!errors.isEmpty()) {
            session.setAttribute(REG_BEAN, regBean);
        } else {
            UserBean user = Service.fillUserBean(regBean);
            if (UserRepository.containsUser(user)) {
                regBean.setEmail("");
                session.setAttribute(REG_BEAN, regBean);
                errors.put(EMAIL, HINT_SAME_EMAIl);
            } else {
                UserRepository.addUser(user);
                session.setAttribute(SUCCESS_REGISTRATION, SUCCESS_REGISTRATION);
            }
        }
        session.setAttribute(ERRORS, errors);
        httpServletResponse.sendRedirect("/registerUser");
    }

    private void removeSessionAttribute(HttpServletRequest httpServletRequest, String key) {
        HttpSession session = httpServletRequest.getSession();
        Object object = session.getAttribute(key);
        if (object != null) {
            httpServletRequest.setAttribute(key, object);
            session.removeAttribute(key);
        }
    }

    private void validateCaptcha(HttpServletRequest httpServletRequest) {
        ServletContext servletContext = httpServletRequest.getServletContext();
        String codeCaptcha = httpServletRequest.getParameter(CAPTCHA);
        CaptchaStrategy strategy = (CaptchaStrategy) servletContext.getAttribute(STRATEGY);
        int idCaptcha = strategy.getIdCaptcha(httpServletRequest);
        long timeout = Long.parseLong(servletContext.getInitParameter(TIMEOUT));
        ValidatorUtil.validateCaptcha(idCaptcha, codeCaptcha, timeout);
    }
}