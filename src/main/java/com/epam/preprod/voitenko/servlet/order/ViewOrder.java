package com.epam.preprod.voitenko.servlet.order;

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

import static com.epam.preprod.voitenko.constant.Constatns.Keys.ERRORS;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.LIST_ORDERED_TOOLS;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.USER_ENTITY;
import static com.epam.preprod.voitenko.constant.Constatns.Message.UNAUTHORIZED_USER;

@WebServlet("/viewOrder")
public class ViewOrder extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forwardTo = getForwardTo(req);
        req.getRequestDispatcher(forwardTo).forward(req, resp);
    }

    private String getForwardTo(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute(USER_ENTITY) == null) {
            setUnauthorizedError(req);
            return "/viewLoginForm";
        } else {
            setListInfoOrderedTool(session);
            return "/WEB-INF/order.jsp";
        }
    }

    private void setUnauthorizedError(HttpServletRequest req) {
        Map<String, String> errors = new HashMap<>();
        errors.put(USER_ENTITY, UNAUTHORIZED_USER);
        req.setAttribute(ERRORS, errors);
    }

    private void setListInfoOrderedTool(HttpSession session) {
        Cart<ElectricToolEntity> cart = ServiceUtil.getCart(session);
        List<InfoOrderedToolEntity> listOrderedTools = getInfoOrderedToolEntities(cart);
        session.setAttribute(LIST_ORDERED_TOOLS, listOrderedTools);
    }

    private List<InfoOrderedToolEntity> getInfoOrderedToolEntities(Cart<ElectricToolEntity> cart) {
        List<InfoOrderedToolEntity> listOrderedTools = new ArrayList<>();
        for (Map.Entry<ElectricToolEntity, Integer> pair : cart.getContent().entrySet()) {
            ElectricToolEntity tool = pair.getKey();
            listOrderedTools.add(new InfoOrderedToolEntity(tool, tool.getCost(), pair.getValue()));
        }
        return listOrderedTools;
    }
}