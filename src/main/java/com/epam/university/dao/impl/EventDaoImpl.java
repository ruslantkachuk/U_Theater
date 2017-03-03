package com.epam.university.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.epam.university.dao.EventDao;
import com.epam.university.entity.Auditorium;
import com.epam.university.entity.Event;
import com.epam.university.entity.Rating;
import com.google.common.collect.Lists;

public class EventDaoImpl implements EventDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Value("${event.create}")
    private String sqlEventCreate;

    @Value("${event.update}")
    private String sqlEventUpdate;

    @Value("${event.remove}")
    private String sqlEventRemove;

    @Value("${event.byName}")
    private String sqlEventByName;

    @Value("${event.all}")
    private String sqlEventAll;

    @Value("${event.forDateRange}")
    private String sqlEventForDateRange;

    public void create(final Event event) {
        jdbcTemplate.update(sqlEventCreate, event.getEventId(), event.getName(), event.getPrice(), event.getRating()
                .toString(), event.getStartTime(), event.getEndTime(), event.getAuditorium().getAuditoriumId());
    }

    @Override
    public void update(final Event event) {
        jdbcTemplate.update(sqlEventUpdate, event.getName(), event.getPrice(), event.getRating().toString(),
                event.getStartTime(), event.getEndTime(), event.getAuditorium().getAuditoriumId(), event.getEventId());
    }

    public void remove(final String eventId) {
        jdbcTemplate.update(sqlEventRemove, eventId);
    }

    public Event getEventByName(final String eventName) {
        return jdbcTemplate.queryForObject(sqlEventByName, new Object[] { eventName }, commonRowMapper());
    }

    public List<Event> getAll() {
        return jdbcTemplate.query(sqlEventAll, commonRowMapper());
    }

    public List<Event> getEventsForDateRange(final Date fromDate, final Date toDate) {
        return jdbcTemplate.query(sqlEventForDateRange, new Object[] { fromDate, toDate }, commonRowMapper());
    }

    private RowMapper<Event> commonRowMapper() {
        return new RowMapper<Event>() {
            public Event mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
                Event event = new Event();
                event.setEventId(resultSet.getString(1));
                event.setName(resultSet.getString(2));
                event.setPrice(resultSet.getDouble(3));
                event.setRating(Rating.valueOf(resultSet.getString(4)));
                event.setStartTime(resultSet.getTimestamp(5));
                event.setEndTime(resultSet.getTimestamp(6));
                Auditorium auditorium = new Auditorium();
                auditorium.setAuditoriumId(resultSet.getString(7));
                auditorium.setName(resultSet.getString(8));
                auditorium.setSeats(resultSet.getInt(9));
                String[] vipSeatsString = resultSet.getString(10).split(AuditoriumDaoImpl.SEPARATOR);
                List<Integer> vipSeatsInteger = Lists.newArrayListWithCapacity(vipSeatsString.length);
                for (String vipSeat : vipSeatsString) {
                    vipSeatsInteger.add(Integer.valueOf(vipSeat));
                }
                auditorium.setVipSeats(vipSeatsInteger);
                event.setAuditorium(auditorium);
                return event;
            }
        };
    }
}
