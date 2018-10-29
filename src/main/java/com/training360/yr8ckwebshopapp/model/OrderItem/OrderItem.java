package com.training360.yr8ckwebshopapp.model.OrderItem;

public class OrderItem {

    private long id;
    private long orderId;
    private long productId;

    public OrderItem() {
    }

    public OrderItem(long id, long orderId, long productId) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getId() {
        return id;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getProductId() {
        return productId;
    }
}
