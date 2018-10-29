package com.training360.yr8ckwebshopapp;

import com.training360.yr8ckwebshopapp.model.OrderItem.OrderItem;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestOrderItem {

    @Test
    public void normalInit() {
        OrderItem orderItem = new OrderItem(1L,1L,1L);
        assertEquals(1L, orderItem.getId());
        assertEquals(1L, orderItem.getOrderId());
        assertEquals(1L, orderItem.getProductId());
    }

    @Test
    public void setThenGetAttributes() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(10L);
        orderItem.setOrderId(10L);
        orderItem.setProductId(10L);
        assertEquals(10L, orderItem.getId());
        assertEquals(10L, orderItem.getOrderId());
        assertEquals(10L, orderItem.getProductId());
    }
}
