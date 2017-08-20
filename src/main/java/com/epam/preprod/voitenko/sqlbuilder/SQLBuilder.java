package com.epam.preprod.voitenko.sqlbuilder;

import java.util.ArrayList;
import java.util.List;

import static com.epam.preprod.voitenko.constant.Constatns.Keys.COST;

public class SQLBuilder {

    private List<String> wheres = new ArrayList<String>();
    private String orderKey = null;
    private String orderDirection = null;
    private String offset = null;
    private String numberRecords = null;

    public SQLBuilder() {
    }

    public SQLBuilder where(String expr) {
        wheres.add(expr);
        return this;
    }

    public SQLBuilder orderBy(String key, String direction) {
        this.orderKey = key;
        this.orderDirection = direction;
        return this;
    }

    public SQLBuilder limit(String offset, String number) {
        this.offset = offset;
        this.numberRecords = number;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder("SELECT SQL_CALC_FOUND_ROWS * FROM tools");
        appendList(sql, wheres, " WHERE ", " AND ");
        appendOrder(sql);
        appendLimit(sql);

        return sql.toString();
    }

    private void appendList(StringBuilder sql, List<String> list, String init, String sep) {
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
        if (orderKey == null) {
            orderKey = COST;
        }
        if (orderDirection == null) {
            orderDirection = "ASC";
        }
        sql.append(" ORDER BY ");
        sql.append(orderKey);
        sql.append(' ');
        sql.append(orderDirection);
    }

    private void appendLimit(StringBuilder sql) {
        if (offset == null) {
            offset = "0";
        }
        if (numberRecords == null) {
            numberRecords = "3";
        }
        int shift = Integer.parseInt(offset) * Integer.parseInt(numberRecords);
        sql.append(" LIMIT ");
        sql.append(shift);
        sql.append(", ");
        sql.append(numberRecords);
        sql.append(';');
    }
}