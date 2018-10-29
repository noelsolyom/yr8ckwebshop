package com.training360.yr8ckwebshopapp.db;

import com.training360.yr8ckwebshopapp.model.Product.Product;
import com.training360.yr8ckwebshopapp.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BasketDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BasketDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Response addProductToUsersBasket(long userId, long productId) {
        try {
            jdbcTemplate.update( new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(
                            "insert into basket (user_id, product_id) " +
                                    "values (?,?)");
                    ps.setLong(1, userId);
                    ps.setLong(2, productId);
                    return ps;
                }
            });
            return new Response("Item successfully added into basket.", true);
        } catch  (DataAccessException dae) {
            return new Response(dae.getMessage(), false);
        }
    }

    public Response emptyUsersBasket(long userId) {
        try {
            jdbcTemplate.update(
                    "Delete from basket " +
                            "where user_id = ?", userId);
            return new Response("Basket is empty.", true);
        } catch (DataAccessException dae) {
            return new Response(dae.getMessage(), false);
        }
    }

    public Response deleteProductFromUsersBasket(long userId, long productId) {
        try {
            jdbcTemplate.update(
                    "Delete from basket " +
                            "where user_id = ? and product_id = ?", userId, productId);
            return new Response("Product has been deleted succesfully.", true);
        } catch (DataAccessException dae){
            return new Response(dae.getMessage(), false);
        }
    }

    public List<Product> listBasketProductsByUserId(long userId) {
        return jdbcTemplate.query(
                "SELECT p.id, p.number, p.name, p.url, p.manufacturer, p.current_price, user_id " +
                        "from product p INNER JOIN basket b ON p.id=b.product_id " +
                        "order by name ASC, manufacturer ASC",
                new ResultSetExtractor<List<Product>>(){
                    @Override
                    public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        List<Product> results = new ArrayList<>();
                        while(rs.next()){
                            if (rs.getLong("user_id") == userId) {
                                results.add(new Product(
                                        rs.getLong("p.id"),
                                        rs.getLong("p.number"),
                                        rs.getString("p.name"),
                                        rs.getString("p.url"),
                                        rs.getString("p.manufacturer"),
                                        rs.getLong("p.current_price")));
                            }
                        }
                        return results;
                    }
                });
    }
}

