package com.epam.preprod.voitenko.servlet.cart;

import com.epam.preprod.voitenko.entity.Cart;
import com.epam.preprod.voitenko.entity.ElectricToolEntity;
import com.epam.preprod.voitenko.handler.DataSourceHandler;
import com.epam.preprod.voitenko.service.ToolService;
import com.epam.preprod.voitenko.util.ServiceUtil;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;

@WebServlet("/addToolToCart")
public class AddToolToCart extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart<ElectricToolEntity> cart = ServiceUtil.getCart(session);
        ElectricToolEntity tool = ServiceUtil.extractElectricToolEntity(req);

        BigDecimal totalCostSpecificTool = tool.getCost();
        BigDecimal quantitySpecificTool = new BigDecimal(cart.addProduct(tool));
        totalCostSpecificTool = totalCostSpecificTool.multiply(quantitySpecificTool);
        totalCostSpecificTool = totalCostSpecificTool.setScale(2, BigDecimal.ROUND_HALF_UP);

        session.setAttribute(CART, cart);
        ServiceUtil.writeJSONObject(resp, cart.getTotalSumPurchase(), cart.getTotalQuantityProducts(), totalCostSpecificTool);
    }
}