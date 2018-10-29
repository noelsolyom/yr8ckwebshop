package com.training360.yr8ckwebshopapp.db;

import com.training360.yr8ckwebshopapp.model.Product.Product;
import com.training360.yr8ckwebshopapp.model.Product.ProductStatus;
import com.training360.yr8ckwebshopapp.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Response createProduct(long number, String name, String url, String manufacturer, long currentPrice) {
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(
                            "insert into product(number, name, url, manufacturer, current_price) " +
                                    "values (?,?,?,?,?)");
                    ps.setLong(1, number);
                    ps.setString(2, name);
                    ps.setString(3, url);
                    ps.setString(4, manufacturer);
                    ps.setLong(5, currentPrice);
                    return ps;
                }
            });
        } catch (DataAccessException dae) {
            return new Response("SQL insert fail", false);
        }
        return new Response("Product has been created", true);
    }

    public Optional<Product> findProductByUrl(String url) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT id, number, name, url, manufacturer, current_price, deleted from product " +
                            "where url = ?", new ProductRowMapper(), url));
        } catch (DataAccessException dae) {
            return Optional.empty();
        }
    }

    public Optional<Product> findProductByNumber(long number) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT id, number, name, url, manufacturer, current_price, deleted from product " +
                            "where number = ?", new ProductRowMapper(), number));
        } catch (DataAccessException dae) {
            return Optional.empty();
        }
    }

    public Optional<Product> findProductById(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT id, number, name, url, manufacturer, current_price, deleted from product " +
                            "where id = ?", new ProductRowMapper(), id));
        } catch (DataAccessException dae) {
            return Optional.empty();
        }
    }

    public List<Product> listProducts() {
        return jdbcTemplate.query(
                "SELECT id, number, name, url, manufacturer, current_price, deleted from product " +
                        "where deleted = 0 order by name ASC, manufacturer ASC", new ProductRowMapper());
    }

    public List<Product> scrollListProducts(long offset, long limit) {
            String query = "select id, number, name, url, manufacturer, current_price, deleted from product " +
                    "where deleted = 0 limit ? offset ?";
            return jdbcTemplate.query(query,
                    new ProductRowMapper(),
                    new Object[]{limit, offset});
    }

    public Response updateProduct(long number, String name, String url, String manufacturer, long currentPrice, long id) {
        try {
            jdbcTemplate.update(
                    "update product set number=?, name=?, url=?, manufacturer=?, current_price=? " +
                            "where id=?", number, name, url, manufacturer, currentPrice, id);
        } catch (Exception e) {
            return new Response(e.getMessage(), false);
        }
        return new Response("Product has been updated.", true);
    }

    public Response deleteProductById(long id) {
        try {
            jdbcTemplate.update(
                    "UPDATE product SET deleted = 1 where id = ?", id);
            return new Response("Product succesfully deleted.", true);
        } catch (DataAccessException dae) {
            return new Response(dae.getMessage(), false);
        }
    }

    public List<Product> listProductsByOrderId(long orderId){
        return jdbcTemplate.query(
                "select p.id, p.number , p.name, p.url, p.manufacturer, p.current_price, p.deleted, " +
                        "order_item.id order_item_id " +
                        "from product p " +
                        "inner join order_item on p.id = order_item.product_id " +
                        "where orders_id = ?", new ProductRowMapper(), orderId);
    }


    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            long number = resultSet.getLong("number");
            String name = resultSet.getString("name");
            String url = resultSet.getString("url");
            String manufacturer = resultSet.getString("manufacturer");
            long currentPrice = resultSet.getLong("current_price");
            ProductStatus productStatus = resultSet.getInt("deleted") == 0 ? ProductStatus.ACTIVE : ProductStatus.DELETED;
            return new Product(id, number, name, url, manufacturer, currentPrice, productStatus);
        }
    }
}
