package com.training360.yr8ckwebshopapp.db;

import com.training360.yr8ckwebshopapp.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class OrderItemDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Response createOrderItem(long orderId, long userId) {
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection
                            .prepareStatement(
                                    "insert into order_item(orders_id, product_id) select ?," +
                                            "basket.product_id from basket " +
                                            "where basket.user_id = ?");
                    ps.setLong(1, orderId);
                    ps.setLong(2, userId);
                    return ps;
                }
            });
        } catch (DataAccessException dae) {
            return new Response(dae.getMessage(), false);
        }
        return new Response("Order item created.", true);
    }

    public Response deleteOrderItem(long orderId, long productId) {
        try {
            jdbcTemplate.update(
                    "DELETE from order_item where orders_id = ? AND product_id = ?", orderId, productId);
            return new Response("Order item succesfully deletedl", true);
        } catch (DataAccessException dae) {
            return new Response(dae.getMessage(), false);
        }
    }
}
