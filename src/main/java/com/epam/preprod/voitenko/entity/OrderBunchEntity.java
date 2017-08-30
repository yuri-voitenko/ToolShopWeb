package com.epam.preprod.voitenko.entity;

public class OrderBunchEntity {
    private int orderID;
    private int infoOrderedToolID;

    public OrderBunchEntity() {
    }

    public OrderBunchEntity(int orderID, int infoOrderedToolID) {
        this.orderID = orderID;
        this.infoOrderedToolID = infoOrderedToolID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getInfoOrderedToolID() {
        return infoOrderedToolID;
    }

    public void setInfoOrderedToolID(int infoOrderedToolID) {
        this.infoOrderedToolID = infoOrderedToolID;
    }
}