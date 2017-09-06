package com.epam.preprod.voitenko.servlet.order;

import com.epam.preprod.voitenko.entity.Cart;
import com.epam.preprod.voitenko.entity.ElectricToolEntity;
import com.epam.preprod.voitenko.entity.InfoOrderedToolEntity;
import com.epam.preprod.voitenko.entity.OrderEntity;
import com.epam.preprod.voitenko.entity.OrderStatus;
import com.epam.preprod.voitenko.entity.UserEntity;
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

import static com.epam.preprod.voitenko.constant.Constatns.Keys.ADDRESS;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.CART;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.DELIVERY;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.LIST_ORDERED_TOOLS;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.ORDER_ENTITY;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.USER_ENTITY;
import static com.epam.preprod.voitenko.util.ServiceUtil.removeSessionAttributeAndSetRequestAttribute;

@WebServlet("/executeOrder")
public class ExecuteOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        removeSessionAttributeAndSetRequestAttribute(req, ORDER_ENTITY);
        removeSessionAttributeAndSetRequestAttribute(req, DELIVERY);
        req.getRequestDispatcher("/viewCompletedOrder").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        OrderEntity orderEntity = getOrderEntity(req, httpSession);
        createNewOrder(orderEntity);
        clearCart(httpSession);
        httpSession.setAttribute(ORDER_ENTITY, orderEntity);
        String delivery = req.getParameter(DELIVERY);
        httpSession.setAttribute(DELIVERY, delivery);
        resp.sendRedirect("/executeOrder");
    }

    private OrderEntity getOrderEntity(HttpServletRequest req, HttpSession httpSession) {
        String address = req.getParameter(ADDRESS);
        List<InfoOrderedToolEntity> listOrderedTools = (List<InfoOrderedToolEntity>) httpSession.getAttribute(LIST_ORDERED_TOOLS);
        UserEntity userEntity = (UserEntity) httpSession.getAttribute(USER_ENTITY);
        return new OrderEntity(OrderStatus.ACCEPTED, "created", address, userEntity, listOrderedTools);
    }

    private void createNewOrder(OrderEntity orderEntity) {
        DataSource dataSource = DataSourceHandler.getInstance().getDataSource();
        OrderService orderService = new OrderService(dataSource);
        orderService.createOrder(orderEntity);
    }

    private void clearCart(HttpSession httpSession) {
        Cart<ElectricToolEntity> cart = ServiceUtil.getCart(httpSession);
        cart.clear();
        httpSession.setAttribute(CART, cart);
    }
}