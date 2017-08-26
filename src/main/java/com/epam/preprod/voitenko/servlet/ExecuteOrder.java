package com.epam.preprod.voitenko.servlet;

import com.epam.preprod.voitenko.entity.*;
import com.epam.preprod.voitenko.handler.DataSourceHandler;
import com.epam.preprod.voitenko.service.OrderService;
import com.epam.preprod.voitenko.util.ServiceUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static com.epam.preprod.voitenko.constant.Constatns.Message.SUCCESS_ORDER;

@WebServlet("/executeOrder")
public class ExecuteOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/viewOrder").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        String address = req.getParameter(ADDRESS);
        List<InfoOrderedToolEntity> listOrderedTools = (List<InfoOrderedToolEntity>) httpSession.getAttribute(LIST_ORDERED_TOOLS);

        UserEntity sss = (UserEntity) httpSession.getAttribute(USER_ENTITY);

        OrderEntity orderEntity = new OrderEntity(OrderStatus.ACCEPTED, "created", address, sss, listOrderedTools);
        DataSource dataSource = DataSourceHandler.getInstance().getDataSource();
        OrderService orderService = new OrderService(dataSource);
        orderService.createOrder(orderEntity);
        Cart<ElectricToolEntity> cart = ServiceUtil.getCart(httpSession);
        cart.clear();
        httpSession.setAttribute(CART, cart);
        req.setAttribute(ORDER_ENTITY, orderEntity);
        req.setAttribute(ORDER_STATE, SUCCESS_ORDER);
        req.getRequestDispatcher("/viewCompletedOrder").forward(req, resp);
    }
}