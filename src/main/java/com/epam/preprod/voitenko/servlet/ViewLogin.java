package com.epam.preprod.voitenko.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/viewLoginForm")
public class ViewLogin extends HttpServlet {

    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest
                .getRequestDispatcher("/WEB-INF/login.jsp")
                .forward(httpServletRequest, httpServletResponse);
    }
}