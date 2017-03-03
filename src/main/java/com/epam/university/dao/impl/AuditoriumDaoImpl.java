package com.epam.university.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.epam.university.dao.AuditoriumDao;
import com.epam.university.entity.Auditorium;
import com.google.common.collect.Lists;

public class AuditoriumDaoImpl implements AuditoriumDao {

    public static final String SEPARATOR = ",";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Value("${auditorium.all}")
    private String sqlAuditoriumAll;

    @Value("${auditorium.create}")
    private String sqlAuditoriumCreate;

    @Resource
    private List<Auditorium> listAuditoriumsRepository;

    @PostConstruct
    public void init() {
        jdbcTemplate.batchUpdate(sqlAuditoriumCreate, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(final PreparedStatement preparedStatement, final int index) throws SQLException {
                Auditorium auditorium = listAuditoriumsRepository.get(index);
                preparedStatement.setString(1, auditorium.getAuditoriumId());
                preparedStatement.setString(2, auditorium.getName());
                preparedStatement.setInt(3, auditorium.getSeats());
                StringBuilder vipSeatsBuilder = new StringBuilder();
                for (Integer vipSeat : auditorium.getVipSeats()) {
                    vipSeatsBuilder.append(String.valueOf(vipSeat) + SEPARATOR);
                }
                preparedStatement.setString(4, vipSeatsBuilder.toString());
            }

            @Override
            public int getBatchSize() {
                return listAuditoriumsRepository.size();
            }
        });
    }

    public List<Auditorium> getAll() {
        List<Auditorium> listAuditoriums = jdbcTemplate.query(sqlAuditoriumAll, new RowMapper<Auditorium>() {
            public Auditorium mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
                Auditorium auditorium = new Auditorium();
                auditorium.setAuditoriumId(resultSet.getString(1));
                auditorium.setName(resultSet.getString(2));
                auditorium.setSeats(resultSet.getInt(3));
                String[] vipSeatsString = resultSet.getString(4).split(SEPARATOR);
                List<Integer> vipSeatsInteger = Lists.newArrayListWithCapacity(vipSeatsString.length);
                for (String vipSeat : vipSeatsString) {
                    vipSeatsInteger.add(Integer.valueOf(vipSeat));
                }
                auditorium.setVipSeats(vipSeatsInteger);
                return auditorium;
            }
        });
        return listAuditoriums;
    }
}
