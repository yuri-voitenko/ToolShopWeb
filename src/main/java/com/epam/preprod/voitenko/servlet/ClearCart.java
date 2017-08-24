package com.epam.preprod.voitenko.servlet;

import com.epam.preprod.voitenko.entity.Cart;
import com.epam.preprod.voitenko.entity.ElectricToolEntity;
import com.epam.preprod.voitenko.util.ServiceUtil;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;

@WebServlet("/clearCart")
public class ClearCart extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart<ElectricToolEntity> cart = ServiceUtil.getCart(session);

        cart.clear();
        session.setAttribute(CART, cart);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CART_TOTAL, cart.getTotalSumPurchase().toString());
        jsonObject.put(CART_QUANTITY, cart.getTotalQuantityProducts().toString());
        Writer writer = resp.getWriter();
        jsonObject.writeJSONString(writer);
    }
}