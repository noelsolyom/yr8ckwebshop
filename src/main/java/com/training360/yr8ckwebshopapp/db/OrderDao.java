package com.training360.yr8ckwebshopapp.db;

import com.training360.yr8ckwebshopapp.model.Order.Order;
import com.training360.yr8ckwebshopapp.model.Order.OrderStatus;
import com.training360.yr8ckwebshopapp.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long createOrder(long userId){

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(
                            "insert into orders (user_id, date) " +
                                    "values(?,?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, userId);
                    ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                    return ps;
                }
            }, keyHolder);

            return keyHolder.getKey().longValue();

        } catch (DataAccessException dae) {
            throw new IllegalStateException("Creating order failed. ", dae);
        }
    }

    public List<Order> listOrdersByUser(long userId){
        return jdbcTemplate.query(
                "select id, user_id, date, status from orders where user_id = ? order by date desc",
                new OrderDao.OrderRowMapper(), userId);
    }

    public List<Order> listActiveOrders() {
        return jdbcTemplate.query(
                "SELECT id, user_id, date, status FROM orders " +
                        "where user_id IS NOT NULL " +
                        "ORDER BY date DESC", new OrderRowMapper());
    }

    public List<Order> listAllOrders() {
        return jdbcTemplate.query(
                "SELECT id, user_id, date, status " +
                        "FROM orders " +
                        "ORDER BY date DESC", new OrderRowMapper());
    }

    public Response deleteOrder(long orderId) {
        try {
            jdbcTemplate.update(
                    "UPDATE orders SET user_id = null where id = ?", orderId);
            return new Response("Order succesfully deleted.", true);
        } catch (DataAccessException dae) {
            return new Response(dae.getMessage(), false);
        }
    }

    public Response archiveOrdersByUserId(long userId){
        try {
            jdbcTemplate.update(
                    "UPDATE orders SET user_id = null where user_id = ? ", userId);
            return new Response("Order(s) has been archived.", true);
        } catch (DataAccessException daee) {
            return new Response(daee.getMessage(), false);
        }
    }

    public Response setOrderDelivered(long orderId) {
        try {
            jdbcTemplate.update(
                    "UPDATE orders SET status = 'DELIVERED' where id = ? ", orderId);
            return new Response("Order has been set to DELIVERED.", true);
        } catch (DataAccessException daee) {
            return new Response(daee.getMessage(), false);
        }
    }

    private static class OrderRowMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            long usersId = resultSet.getLong("user_id");
            LocalDateTime time = resultSet.getTimestamp("date").toLocalDateTime();
            String orderStatus = resultSet.getString("status");
            return new Order(id, usersId, time, OrderStatus.valueOf(orderStatus));
        }
    }

}
