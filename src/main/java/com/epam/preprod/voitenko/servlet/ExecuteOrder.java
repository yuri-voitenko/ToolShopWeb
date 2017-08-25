package com.epam.preprod.voitenko.servlet;

import com.epam.preprod.voitenko.entity.InfoOrderedToolEntity;
import com.epam.preprod.voitenko.entity.OrderEntity;
import com.epam.preprod.voitenko.entity.OrderStatus;
import com.epam.preprod.voitenko.entity.UserEntity;
import com.epam.preprod.voitenko.handler.DataSourceHandler;
import com.epam.preprod.voitenko.service.OrderService;

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
import static com.epam.preprod.voitenko.constant.Constatns.Keys.LIST_ORDERED_TOOLS;

@WebServlet("/executeOrder")
public class ExecuteOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        String address = req.getParameter(ADDRESS);
        List<InfoOrderedToolEntity> listOrderedTools = (List<InfoOrderedToolEntity>) httpSession.getAttribute(LIST_ORDERED_TOOLS);

        UserEntity sss = new UserEntity("aaaaaat@gmail.com", "Voitenko!335", "Yuri Voitenko", "+380505730182", "Kharkiv, Puskinska st., 79");
        sss.setId(1);

        OrderEntity orderEntity = new OrderEntity(OrderStatus.ACCEPTED, "created", "asdasdasd", sss, listOrderedTools);
        DataSource dataSource = DataSourceHandler.getInstance().getDataSource();
        OrderService orderService = new OrderService(dataSource);
        orderService.createOrder(orderEntity);
        resp.sendRedirect("/executeOrder");
    }
}