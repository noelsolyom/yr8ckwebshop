package com.training360.yr8ckwebshopapp;

import com.training360.yr8ckwebshopapp.ui.OrderController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "classpath:/clearDatabase.sql")
public class TestOrderController {

    @Autowired
    private OrderController orderController;

    @Test
    public void createOrder() {
        assertTrue(orderController.createOrder(1).isOk());
        assertEquals("Order has been created.", orderController.createOrder(1).getMessage());
    }

    @Test
    public void createOrderNonexistingUser() {
        assertFalse(orderController.createOrder(100000).isOk());
        assertEquals("Creating order failed. Creating order item(s) failed", orderController.createOrder(1000000).getMessage());
    }

    @Test
    public void createOrderInvalidUser() {
        assertFalse(orderController.createOrder(-1).isOk());
        assertEquals("Creating order failed. Creating order item(s) failed", orderController.createOrder(1000000).getMessage());
    }

    @Test
    public void listOrdersByUserId() {
        assertEquals(2L, orderController.listOrdersByUserId(1L).size());
    }

    @Test
    public void listProductsByOrderId() {
        assertEquals(3, orderController.listProductsByOrderId(1).size());
    }

    @Test
    public void listActiveOrders() {
        orderController.deleteOrder(1);
        assertEquals(2L, orderController.listActiveOrders().size());
    }

    @Test
    public void listAll() {
      orderController.deleteOrder(1L);
       assertEquals(3, orderController.listAllOrders().size());
    }

    @Test
    public void deleteOrderItem() {
        orderController.deleteOrderItemFromOrder(1,3);
        assertEquals(2, orderController.listProductsByOrderId(1).size());
    }

    @Test
    public void deleteOrder() {
        assertEquals(2L, orderController.listOrdersByUserId(1).size());
        orderController.deleteOrder(1);
        assertEquals(1L, orderController.listOrdersByUserId(1).size());
    }

    @Test
    public void setOrderDelivered() {
        assertTrue(orderController.setOrderDelivered(2).isOk());
        assertEquals("Order has been set to DELIVERED.", orderController.setOrderDelivered(3).getMessage());
    }
}
