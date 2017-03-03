package com.epam.university.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.epam.university.dao.UserDao;
import com.epam.university.entity.User;

public class UserDaoImpl implements UserDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Value("${user.create}")
    private String sqlUserCreate;

    @Value("${user.remove}")
    private String sqlUserRemove;

    @Value("${user.byId}")
    private String sqlUserById;

    @Value("${user.byEmail}")
    private String sqlUserByEmail;

    @Value("${user.byName}")
    private String sqlUserByName;

    public void create(final User user) {
        jdbcTemplate.update(sqlUserCreate, user.getUserId(), user.getName(), user.getSurname(), user.getBirthday(),
                user.getEmail());
    }

    public void remove(final String userId) {
        jdbcTemplate.update(sqlUserRemove, userId);
    }

    public User getUserById(final String userId) {
        return jdbcTemplate.queryForObject(sqlUserById, new Object[] { userId }, commonRowMapper());
    }

    public User getUserByEmail(final String userEmail) {
        return jdbcTemplate.queryForObject(sqlUserByEmail, new Object[] { userEmail }, commonRowMapper());
    }

    public User getUserByName(final String userName) {
        return jdbcTemplate.queryForObject(sqlUserByName, new Object[] { userName }, commonRowMapper());
    }

    private RowMapper<User> commonRowMapper() {
        return new RowMapper<User>() {
            public User mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
                User user = new User();
                user.setUserId(resultSet.getString(1));
                user.setName(resultSet.getString(2));
                user.setSurname(resultSet.getString(3));
                user.setBirthday(resultSet.getTimestamp(4));
                user.setEmail(resultSet.getString(5));
                return user;
            }
        };
    }
}
