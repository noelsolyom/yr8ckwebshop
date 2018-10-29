package com.training360.yr8ckwebshopapp;

import com.training360.yr8ckwebshopapp.model.Order.Order;
import com.training360.yr8ckwebshopapp.model.Order.OrderStatus;
import org.junit.Test;
import java.time.LocalDateTime;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class TestOrder {

    private LocalDateTime localDateTime = LocalDateTime.now();

    @Test
    public void normalInit() {
        Order order = new Order(1,1, localDateTime, OrderStatus.ACTIVE);
        assertEquals(1L, order.getId());
        assertEquals(1L, order.getUserId());
        assertEquals(localDateTime, order.getDate());
        assertThat(order.getOrderStatus(), equalTo(OrderStatus.ACTIVE));
    }

    @Test
    public void setThenGetAttributes() {
        Order order = new Order();
        order.setId(10L);
        order.setUserId(10L);
        order.setDate(localDateTime);
        order.setOrderStatus(OrderStatus.DELIVERED);
        assertEquals(10L, order.getId());
        assertEquals(10L, order.getUserId());
        assertThat(order.getDate(), equalTo(localDateTime));
        assertThat(order.getOrderStatus(), equalTo(OrderStatus.DELIVERED));
    }
}
