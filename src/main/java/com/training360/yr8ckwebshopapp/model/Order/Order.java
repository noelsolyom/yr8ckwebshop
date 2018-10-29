package com.training360.yr8ckwebshopapp.model.Order;

import java.time.LocalDateTime;

public class Order {

    private long id;
    private long userId;
    private LocalDateTime date;
    private OrderStatus orderStatus;

    public Order() {
    }

    public Order(long id, long userId, LocalDateTime date, OrderStatus orderStatus) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.orderStatus = orderStatus;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
