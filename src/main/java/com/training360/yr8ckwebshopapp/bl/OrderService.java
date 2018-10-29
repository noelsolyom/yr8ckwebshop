package com.training360.yr8ckwebshopapp.bl;

import com.training360.yr8ckwebshopapp.db.BasketDao;
import com.training360.yr8ckwebshopapp.db.OrderDao;
import com.training360.yr8ckwebshopapp.db.OrderItemDao;
import com.training360.yr8ckwebshopapp.db.ProductDao;
import com.training360.yr8ckwebshopapp.model.Order.Order;
import com.training360.yr8ckwebshopapp.model.Product.Product;
import com.training360.yr8ckwebshopapp.response.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderDao orderDao;
    private OrderItemDao orderItemDao;
    private BasketDao basketDao;
    private ProductDao productDao;

    public OrderService(OrderDao orderDao, OrderItemDao orderItemDao, BasketDao basketDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
        this.basketDao = basketDao;
        this.productDao = productDao;
    }

    public Response createOrder(long userId) {
        long orderId = 0;
        try {
            orderId = orderDao.createOrder(userId);
        } catch (IllegalStateException ise) {
            return new Response(ise.getMessage() + "Creating order item(s) failed", false);
        }
        if (orderId == 0) {
            return new Response("Retriving order id failed.", false);
        }

        Response response = orderItemDao.createOrderItem(orderId, userId);
        basketDao.emptyUsersBasket(userId);
        response.setMessage("Order has been created.");

        return response;
    }

    public List<Order> listOrdersByUserId(long userId) {
        return orderDao.listOrdersByUser(userId);
    }

    public List<Product> listProductsByOrderId(long orderId) {
        return productDao.listProductsByOrderId(orderId);
    }

    public List<Order> listActiveOrders(){
        return orderDao.listActiveOrders();
    }

    public List<Order> listAllOrders(){
        return orderDao.listAllOrders();
    }

    public Response deleteOrder(long orderId) {
            return orderDao.deleteOrder(orderId);
    }

    public Response setOrderDelivered(long orderId) {
        return orderDao.setOrderDelivered(orderId);
    }
}
