package com.epam.preprod.voitenko.servlet;

import com.epam.preprod.voitenko.entity.FilterEntity;
import com.epam.preprod.voitenko.handler.DataSourceHandler;
import com.epam.preprod.voitenko.service.ToolService;
import com.epam.preprod.voitenko.util.ServiceUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.*;
import static com.epam.preprod.voitenko.util.ValidatorUtil.isNullOrEmpty;

@WebServlet("/viewTools")
public class ViewTools extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterEntity filterEntity = ServiceUtil.extractFilterEntity(req);
        //TODO: add validation for request
        //!ValidatorUtil.validate(filterEntity))
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

    private int getAmountPages(long numberSuitableTools, FilterEntity filterEntity) {
        int amountPages;
        int numberToolsOnPage = 3;
        String strNumberToolsOnPage = filterEntity.getNumberToolsOnPage();
        if (!isNullOrEmpty(strNumberToolsOnPage)) {
            numberToolsOnPage = Integer.parseInt(strNumberToolsOnPage);
        }
        amountPages = (int) numberSuitableTools / numberToolsOnPage;
        if (numberSuitableTools % numberToolsOnPage != 0) {
            amountPages++;
        }
        return amountPages;
    }
}