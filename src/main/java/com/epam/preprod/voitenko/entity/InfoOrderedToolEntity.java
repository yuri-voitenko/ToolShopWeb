package com.epam.preprod.voitenko.entity;

import java.math.BigDecimal;

public final class InfoOrderedToolEntity {
    private final ElectricToolEntity electricTool;
    private final Integer amount;
    private final BigDecimal unitPrice;

    public InfoOrderedToolEntity(ElectricToolEntity electricTool, Integer amount, BigDecimal unitPrice) {
        this.electricTool = electricTool;
        this.amount = amount;
        this.unitPrice = unitPrice;
    }

    public ElectricToolEntity getElectricTool() {
        return electricTool;
    }

    public Integer getAmount() {
        return amount;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}