package com.epam.preprod.voitenko.entity;

import java.math.BigDecimal;

public final class InfoOrderedToolEntity {
    private final int id;
    private final ElectricToolEntity electricTool;
    private final BigDecimal unitPrice;
    private final Integer amount;


    public InfoOrderedToolEntity(ElectricToolEntity electricTool, BigDecimal unitPrice, Integer amount) {
        this.id = 0;
        this.electricTool = electricTool;
        this.unitPrice = unitPrice;
        this.amount = amount;
    }

    public InfoOrderedToolEntity(int id, ElectricToolEntity electricTool, BigDecimal unitPrice, Integer amount) {
        this.id = id;
        this.electricTool = electricTool;
        this.unitPrice = unitPrice;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public ElectricToolEntity getElectricTool() {
        return electricTool;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Integer getAmount() {
        return amount;
    }
}