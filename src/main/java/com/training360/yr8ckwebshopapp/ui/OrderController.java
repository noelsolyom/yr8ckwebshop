package com.training360.yr8ckwebshopapp.ui;

import com.training360.yr8ckwebshopapp.bl.OrderItemService;
import com.training360.yr8ckwebshopapp.bl.OrderService;
import com.training360.yr8ckwebshopapp.model.Order.Order;
import com.training360.yr8ckwebshopapp.model.Product.Product;
import com.training360.yr8ckwebshopapp.response.Response;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class OrderController {

    private OrderService orderService;
    private OrderItemService orderItemService;

    public OrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    //      http://localhost:8080/myorders?userId=2
    @RequestMapping(value = "/myorders", method = RequestMethod.POST)
    public Response createOrder(@RequestParam long userId){
        return orderService.createOrder(userId);
    }

    //      http://localhost:8080/myorders?userId=1
    @RequestMapping(value = "/myorders", method = RequestMethod.GET)
    public List<Order> listOrdersByUserId(@RequestParam long userId){
        return orderService.listOrdersByUserId(userId);
    }

    //      http://localhost:8080/myorders/12
    @RequestMapping(value = "/myorders/{orderId}", method = RequestMethod.GET)
    public List<Product> listProductsByOrderId(@PathVariable long orderId){
        return orderService.listProductsByOrderId(orderId);
    }

    //      http://localhost:8080/orders
    @RequestMapping( value = "/orders", method = RequestMethod.GET)
    public List<Order> listActiveOrders() {
        return orderService.listActiveOrders();
    }

    //      http://localhost:8080/orders/all
    @RequestMapping( value = "/orders/all", method = RequestMethod.GET)
    public List<Order> listAllOrders() {
        return orderService.listAllOrders();
    }

    //      http://localhost:8080/orders?orderId=7
    @RequestMapping( value = "/orders", method = RequestMethod.DELETE)
    public Response deleteOrder(@RequestParam long orderId) {
        return orderService.deleteOrder(orderId);
    }

    //      http://localhost:8080/orders/2/23
    @RequestMapping( value = "/orders/{orderId}/{productId}", method = RequestMethod.DELETE)
    public Response deleteOrderItemFromOrder(@PathVariable long orderId, @PathVariable long productId) {
        return orderItemService.deleteOrderItem(orderId, productId);
    }

    //      http://localhost:8080/orders/{orderId}/status
    @RequestMapping( value = "orders/{orderId}/status", method = RequestMethod.POST)
    public Response setOrderDelivered(@PathVariable long orderId) {
        return orderService.setOrderDelivered(orderId);
    }
}
