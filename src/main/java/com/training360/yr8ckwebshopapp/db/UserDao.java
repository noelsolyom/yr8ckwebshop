package com.training360.yr8ckwebshopapp.db;

import com.training360.yr8ckwebshopapp.model.User.User;
import com.training360.yr8ckwebshopapp.model.User.UserRole;
import com.training360.yr8ckwebshopapp.model.User.UserStatus;
import com.training360.yr8ckwebshopapp.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> listUsers() {
        return jdbcTemplate.query(
                "SELECT id, user_name, role, family_name, given_name, deleted from user " +
                        "where deleted = 0 " +
                        "order by id ASC", new UserRowMapper());
    }

    public Optional<User> findUserByUserName(String userName) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "Select id, user_name, role, family_name, given_name, deleted from user " +
                            "where user_name = ?"
                    , new UserRowMapper(), userName));
        } catch (DataAccessException dae) {
            return Optional.empty();
        }
    }

    public Response createUser(String userName, String password, String familyName, String givenName) {
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(
                            "insert into user (user_name, password, family_name, given_name) values (?,?,?,?)");

                    String encodedPassword = new BCryptPasswordEncoder().encode(password);
                    ps.setString(1, userName);
                    ps.setString(2, encodedPassword);
                    ps.setString(3, familyName);
                    ps.setString(4, givenName);
                    return ps;
                }
            });
        } catch (DataAccessException dae) {
            return new Response(dae.getMessage(), false);
        }
        return new Response("User has been created.", true);
    }

    public Response updateUserWithoutPassword(String familyName, String givenName, long userId) {
        try {
            jdbcTemplate.update(
                    "UPDATE user SET family_name=?, given_name=? where id = ?",
                    familyName, givenName, userId);
            return new Response("User update succesfull.", true);
        } catch (DataAccessException dae) {
            return new Response(dae.getMessage(), false);
        }
    }
    public Response updateUserWithPassword(String password, String familyName, String givenName, long userId) {
        try {
            String encoded = new BCryptPasswordEncoder().encode(password);
            jdbcTemplate.update(
                    "UPDATE user SET password = ?, family_name=?, given_name=? where id = ?",
                    encoded, familyName, givenName, userId);
            return new Response("User update succesfull.", true);
        } catch (DataAccessException dae) {
            return new Response(dae.getMessage(), false);
        }
    }

    public Response deleteUser(long userId) {
        try {
            jdbcTemplate.update(
                    "UPDATE user SET user_name = ?, password=null, role=null, family_name=null, given_name=null,  deleted = 1 " +
                            "where id = ?", userId, userId);
            return new Response("User successfully deleted.", true);
        } catch (DataAccessException daee) {
            return new Response(daee.getMessage(), false);
        }
    }

    public Optional<User> findUserByUserId(long userId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "Select id, user_name, role, family_name, given_name, deleted from user " +
                            "where deleted = 0 AND id = ?"
                    , new UserRowMapper(), userId));
        } catch (DataAccessException dae) {
            return Optional.empty();
        }
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String userName = resultSet.getString("user_name");
            UserRole userRole = resultSet.getString("role").equals("ADMIN") ?
                    UserRole.ADMIN : UserRole.USER;
            String familyName = resultSet.getString("family_name");
            String givenName = resultSet.getString("given_name");
            UserStatus userStatus = resultSet.getInt("deleted") == 0 ?
                    UserStatus.ACTIVE : UserStatus.DELETED;
            return new User(id, userName, userRole, familyName, givenName, userStatus);
        }
    }
}
