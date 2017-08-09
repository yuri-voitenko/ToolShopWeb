package com.epam.preprod.voitenko.servlet;

import com.epam.preprod.voitenko.bean.RegisterBean;
import com.epam.preprod.voitenko.bean.UserBean;
import com.epam.preprod.voitenko.repository.UserRepository;
import com.epam.preprod.voitenko.service.Service;
import com.epam.preprod.voitenko.strategy.CaptchaStrategy;
import com.epam.preprod.voitenko.validate.ValidatorUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/registerUser")
public class RegistrationUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        RegisterBean regBean = Service.extractRegisterBean(httpServletRequest);
        Map<String, String> errors = ValidatorUtil.validate(regBean);

        validateCaptcha(httpServletRequest);

        if (!errors.isEmpty()) {
            httpServletRequest.setAttribute("regBean", regBean);
        } else {
            UserBean user = Service.fillUserBean(regBean);
            if (UserRepository.containsUser(user)) {
                regBean.setEmail("");
                httpServletRequest.setAttribute("regBean", regBean);
                errors.put("Email", "Registration fail!<br>User with such login(email) already exists!");
            } else {
                UserRepository.addUser(user);
                httpServletRequest.setAttribute("successRegistration", "Registration completed successfully!");
            }
        }
        httpServletRequest.setAttribute("errors", errors);
        getServletContext().getRequestDispatcher("/viewRegisterForm").forward(httpServletRequest, httpServletResponse);
    }

    private void validateCaptcha(HttpServletRequest httpServletRequest) {
        String codeCaptcha = httpServletRequest.getParameter("captcha");
        CaptchaStrategy strategy = (CaptchaStrategy) getServletContext().getAttribute("strategy");
        int idCaptcha = strategy.getIdCaptcha(httpServletRequest);
        long timeout = Long.parseLong(getServletContext().getInitParameter("Timeout"));
        ValidatorUtil.validateCaptcha(idCaptcha, codeCaptcha, timeout);
    }
}