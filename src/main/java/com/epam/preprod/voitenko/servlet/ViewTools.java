package com.epam.preprod.voitenko.servlet;

import com.epam.preprod.voitenko.entity.FilterEntity;
import com.epam.preprod.voitenko.handler.DataSourceHandler;
import com.epam.preprod.voitenko.service.ToolService;
import com.epam.preprod.voitenko.util.ServiceUtil;
import com.epam.preprod.voitenko.util.ValidatorUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/viewTools")
public class ViewTools extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterEntity filterEntity = ServiceUtil.extractFilterEntity(req);
        FilterEntity empty = new FilterEntity();
        ValidatorUtil.validate(filterEntity);

        System.out.println(filterEntity);
        System.out.println(ServiceUtil.createSQL(filterEntity));
        DataSource dataSource = DataSourceHandler.getInstance().getDataSource();
        ToolService toolService = new ToolService(dataSource);
        req.setAttribute("categories", toolService.getCategories());
        req.setAttribute("manufacturers", toolService.getManufacturers());
        req.setAttribute("tools", toolService.getAllTools());
        req.getRequestDispatcher("/WEB-INF/products.jsp").forward(req, resp);
    }
}