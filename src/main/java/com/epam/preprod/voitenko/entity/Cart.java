package com.epam.preprod.voitenko.entity;

import java.math.BigDecimal;
import java.util.Map;

public interface Cart<T> {
    Integer addProduct(T entity);

    Integer deleteProduct(T entity);

    Integer reduceQuantityProduct(T entity);

    void clear();

    Map<T, Integer> getContent();

    BigDecimal getTotalSumPurchase();

    Integer getTotalQuantityProducts();
}