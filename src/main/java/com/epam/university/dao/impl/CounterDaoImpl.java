package com.epam.university.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.epam.university.dao.CounterDao;
import com.epam.university.entity.Counter;

public class CounterDaoImpl implements CounterDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Value("${counter.create}")
    private String sqlCounterCreate;

    @Value("${counter.update}")
    private String sqlCounterUpdate;

    @Value("${counter.byId.entityId.entityType}")
    private String sqlCounterByIdEntityIdEntityType;

    public void create(final Counter counter) {
        jdbcTemplate.update(sqlCounterCreate, counter.getCounterId(), counter.getEntityId(), counter.getEntityType(),
                counter.getEntityCount());
    }

    public void update(final Counter counter) {
        jdbcTemplate.update(sqlCounterUpdate, counter.getEntityId(), counter.getEntityType(), counter.getEntityCount(),
                counter.getCounterId());
    }

    public Counter getCounterByIdAndEntityIdAndEntityType(final String counterId, final String entityId,
            final String entityType) {
        Counter resultCounter = jdbcTemplate.queryForObject(sqlCounterByIdEntityIdEntityType, new Object[] { counterId,
                entityId, entityType }, new RowMapper<Counter>() {
            public Counter mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
                Counter counter = new Counter();
                counter.setCounterId(resultSet.getString(1));
                counter.setEntityId(resultSet.getString(2));
                counter.setEntityType(resultSet.getString(3));
                counter.setEntityCount(resultSet.getInt(4));
                return counter;
            }
        });
        return resultCounter;
    }
}
