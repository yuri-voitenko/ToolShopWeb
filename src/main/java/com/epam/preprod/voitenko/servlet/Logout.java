package com.epam.preprod.voitenko.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.USER_BEAN;
import static com.epam.preprod.voitenko.service.Service.removeSessionAttribute;

@WebServlet("/logoutUser")
public class Logout extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        removeSessionAttribute(req, USER_BEAN);
        req.getRequestDispatcher("/viewHomePage").forward(req, resp);
    }
}