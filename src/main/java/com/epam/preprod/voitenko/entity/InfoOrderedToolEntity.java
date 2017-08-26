package com.epam.preprod.voitenko.entity;

import java.math.BigDecimal;

public final class InfoOrderedToolEntity {
    private int id;
    private final ElectricToolEntity electricTool;
    private final BigDecimal unitPrice;
    private final Integer amount;

    public InfoOrderedToolEntity(int id, ElectricToolEntity electricTool, BigDecimal unitPrice, Integer amount) {
        this.id = id;
        this.electricTool = electricTool;
        this.unitPrice = unitPrice;
        this.amount = amount;
    }

    public InfoOrderedToolEntity(ElectricToolEntity electricTool, BigDecimal unitPrice, Integer amount) {
        this.electricTool = electricTool;
        this.unitPrice = unitPrice;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ElectricToolEntity getElectricTool() {
        return electricTool;
    }

    public InfoOrderedToolEntity setElectricTool(ElectricToolEntity electricTool) {
        return new InfoOrderedToolEntity(id, electricTool, unitPrice, amount);
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public InfoOrderedToolEntity setUnitPrice(BigDecimal unitPrice) {
        return new InfoOrderedToolEntity(id, electricTool, unitPrice, amount);
    }

    public Integer getAmount() {
        return amount;
    }

    public InfoOrderedToolEntity setAmount(Integer amount) {
        return new InfoOrderedToolEntity(id, electricTool, unitPrice, amount);
    }
}