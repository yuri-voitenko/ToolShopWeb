package com.epam.preprod.voitenko.sqlbuilder;

import java.util.ArrayList;
import java.util.List;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.COST;

public class SQLBuilder {

    private List<String> wheres = new ArrayList<String>();
    private String orderKey = null;
    private String orderDirection = null;

    public SQLBuilder() {
    }

    public SQLBuilder orderBy(String key, String direction) {
        this.orderKey = key;
        this.orderDirection = direction;
        return this;
    }

    @Override
    public String toString() {

        StringBuilder sql = new StringBuilder("SELECT SQL_CALC_FOUND_ROWS * FROM tools");

        appendList(sql, wheres, " WHERE ", " AND ");

        if (orderKey == null) {
            orderKey = COST;
        }
        if (orderDirection == null) {
            orderDirection = "ASC";
        }
        appendOrder(sql);


        return sql.toString();
    }

    private void appendList(StringBuilder sql, List<String> list, String init,
                            String sep) {
        boolean first = true;
        for (String s : list) {
            if (first) {
                sql.append(init);
            } else {
                sql.append(sep);
            }
            sql.append(s);
            first = false;
        }
    }

    private void appendOrder(StringBuilder sql) {
        sql.append(" ORDER BY ");
        sql.append(orderKey);
        sql.append(' ');
        sql.append(orderDirection);
        sql.append(';');
    }

    public SQLBuilder where(String expr) {
        wheres.add(expr);
        return this;
    }
}