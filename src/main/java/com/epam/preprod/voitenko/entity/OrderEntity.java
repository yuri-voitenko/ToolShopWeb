package com.epam.preprod.voitenko.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class OrderEntity {
    private int id;
    private OrderStatus status;
    private String detailStatus;
    private String address;
    private Timestamp dateTime;
    private UserEntity user;
    private List<InfoOrderedToolEntity> orders;

    public OrderEntity() {
    }

    public OrderEntity(OrderStatus status, String detailStatus, String address, UserEntity user, List<InfoOrderedToolEntity> orders) {
        this.status = status;
        this.detailStatus = detailStatus;
        this.address = address;
        this.dateTime = getCurrentTimestamp();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    private Timestamp getCurrentTimestamp() {
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        return new Timestamp(timeStampMillis);
    }
}