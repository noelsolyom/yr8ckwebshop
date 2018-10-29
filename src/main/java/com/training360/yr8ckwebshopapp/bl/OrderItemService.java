package com.training360.yr8ckwebshopapp.bl;

import com.training360.yr8ckwebshopapp.db.OrderDao;
import com.training360.yr8ckwebshopapp.db.OrderItemDao;
import com.training360.yr8ckwebshopapp.response.Response;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    private OrderItemDao orderItemDao;
    private OrderDao orderDao;

    public OrderItemService(OrderItemDao orderItemDao, OrderDao orderDao) {
        this.orderItemDao = orderItemDao;
        this.orderDao = orderDao;
    }

    public Response deleteOrderItem(long orderId, long productId) {
        return orderItemDao.deleteOrderItem(orderId, productId);
    }
}
