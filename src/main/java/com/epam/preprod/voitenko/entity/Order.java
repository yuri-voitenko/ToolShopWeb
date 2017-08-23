package com.epam.preprod.voitenko.entity;

import java.sql.Timestamp;
import java.util.List;

public class Order {
    private int id;
    private OrderStatus status;
    private String detailStatus;
    private Timestamp dateTime;
    private UserEntity user;
    private List<InfoOrderedToolEntity> orders;

    public Order(OrderStatus status, String detailStatus, Timestamp dateTime, UserEntity user, List<InfoOrderedToolEntity> orders) {
        this.status = status;
        this.detailStatus = detailStatus;
        this.dateTime = dateTime;
        this.user = user;
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getDetailStatus() {
        return detailStatus;
    }

    public void setDetailStatus(String detailStatus) {
        this.detailStatus = detailStatus;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<InfoOrderedToolEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<InfoOrderedToolEntity> orders) {
        this.orders = orders;
    }
}