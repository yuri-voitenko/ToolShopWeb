package com.epam.preprod.voitenko.servlet;

import com.epam.preprod.voitenko.entity.Cart;
import com.epam.preprod.voitenko.entity.ElectricToolEntity;
import com.epam.preprod.voitenko.entity.InfoOrderedToolEntity;
import com.epam.preprod.voitenko.util.ServiceUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static com.epam.preprod.voitenko.constant.Constatns.Message.UNAUTHORIZED_USER;

@WebServlet("/viewOrder")
public class ViewOrder extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute(USER_ENTITY) == null) {
            Map<String, String> errors = new HashMap<>();
            errors.put(USER_ENTITY, UNAUTHORIZED_USER);
            req.setAttribute(ERRORS, errors);
            req.getRequestDispatcher("/viewLoginForm").forward(req, resp);
        } else {
            Cart<ElectricToolEntity> cart = ServiceUtil.getCart(session);
            List<InfoOrderedToolEntity> listOrderedTools = new ArrayList<>();
            for (Map.Entry<ElectricToolEntity, Integer> pair : cart.getContent().entrySet()) {
                ElectricToolEntity tool = pair.getKey();
                listOrderedTools.add(new InfoOrderedToolEntity(tool, tool.getCost(), pair.getValue()));
            }
            session.setAttribute(LIST_ORDERED_TOOLS, listOrderedTools);
            req.getRequestDispatcher("/WEB-INF/order.jsp").forward(req, resp);
        }
    }
}