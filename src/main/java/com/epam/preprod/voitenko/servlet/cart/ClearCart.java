package com.epam.preprod.voitenko.servlet.cart;

import com.epam.preprod.voitenko.entity.Cart;
import com.epam.preprod.voitenko.entity.ElectricToolEntity;
import com.epam.preprod.voitenko.util.ServiceUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.CART;

@WebServlet("/clearCart")
public class ClearCart extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart<ElectricToolEntity> cart = ServiceUtil.getCart(session);
        cart.clear();
        session.setAttribute(CART, cart);
        ServiceUtil.writeJSONObject(resp, cart.getTotalSumPurchase(), cart.getTotalQuantityProducts(), BigDecimal.ZERO);
    }
}