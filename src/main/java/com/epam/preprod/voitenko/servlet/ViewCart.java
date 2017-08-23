package com.epam.preprod.voitenko.servlet;

import com.epam.preprod.voitenko.entity.CartEntity;
import com.epam.preprod.voitenko.entity.ElectricToolEntity;
import com.epam.preprod.voitenko.handler.DataSourceHandler;
import com.epam.preprod.voitenko.service.ToolService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/viewCart")
public class ViewCart extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        CartEntity cart = (CartEntity) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartEntity();
        }
        String strToolID = req.getParameter("id");
        Integer toolID = 0;
        if (strToolID != null) {
            toolID = Integer.parseInt(strToolID);
        }

        DataSource dataSource = DataSourceHandler.getInstance().getDataSource();
        ToolService toolService = new ToolService(dataSource);
        ElectricToolEntity tool = toolService.getToolById(toolID);
        cart.addProduct(tool);
        session.setAttribute("cart", cart);
        req.getRequestDispatcher("/WEB-INF/cart.jsp").forward(req, resp);

    }
}