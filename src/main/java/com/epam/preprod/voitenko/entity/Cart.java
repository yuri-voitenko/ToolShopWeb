package com.epam.preprod.voitenko.entity;

import java.math.BigDecimal;
import java.util.Map;

public interface Cart<T> {
    Integer addProduct(T entity);

    Integer deleteProduct(T entity);

    Map<T, Integer> getContent();

    BigDecimal getTotalSumPurchase();
}