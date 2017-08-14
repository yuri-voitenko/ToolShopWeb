package com.epam.preprod.voitenko.servlet;

import com.epam.preprod.voitenko.strategy.ICaptchaStrategy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.STRATEGY;

@WebServlet("/viewRegisterForm")
public class ViewRegister extends HttpServlet {

    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        ICaptchaStrategy strategy = (ICaptchaStrategy) getServletContext().getAttribute(STRATEGY);
        strategy.setIdCaptcha(httpServletRequest, httpServletResponse);
        httpServletRequest
                .getRequestDispatcher("/WEB-INF/register.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }
}