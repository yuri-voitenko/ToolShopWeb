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

import static com.epam.preprod.voitenko.constant.Constatns.Keys.AMOUNT_PAGES;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.CATEGORIES;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.FILTER_ENTITY;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.MANUFACTURERS;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.NUMBER_SUITABLE_TOOLS;
import static com.epam.preprod.voitenko.constant.Constatns.Keys.TOOLS;

@WebServlet("/viewTools")
public class ViewTools extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterEntity filterEntity = ServiceUtil.extractFilterEntity(req);
        if (!ValidatorUtil.validate(filterEntity)) {
            resp.sendRedirect("/badRequest");
        } else {
            String sql = ServiceUtil.createSQL(filterEntity);
            DataSource dataSource = DataSourceHandler.getInstance().getDataSource();
            ToolService toolService = new ToolService(dataSource);
            req.setAttribute(FILTER_ENTITY, filterEntity);
            req.setAttribute(CATEGORIES, toolService.getCategories());
            req.setAttribute(MANUFACTURERS, toolService.getManufacturers());
            req.setAttribute(TOOLS, toolService.getToolsByFilter(sql));
            long numberSuitableTools = toolService.getNumberSuitableTools(sql);
            req.setAttribute(NUMBER_SUITABLE_TOOLS, numberSuitableTools);
            req.setAttribute(AMOUNT_PAGES, getAmountPages(numberSuitableTools, filterEntity));
            req.getRequestDispatcher("/WEB-INF/products.jsp").forward(req, resp);
        }
    }

    private int getAmountPages(long numberSuitableTools, FilterEntity filterEntity) {
        int amountPages;
        String strNumberToolsOnPage = filterEntity.getNumberToolsOnPage();
        int numberToolsOnPage = Integer.parseInt(strNumberToolsOnPage);
        amountPages = (int) numberSuitableTools / numberToolsOnPage;
        if (numberSuitableTools % numberToolsOnPage != 0) {
            amountPages++;
        }
        return amountPages;
    }
}