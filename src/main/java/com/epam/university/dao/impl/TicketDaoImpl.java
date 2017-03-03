package com.epam.university.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.epam.university.dao.TicketDao;
import com.epam.university.entity.*;
import com.google.common.collect.Lists;

public class TicketDaoImpl implements TicketDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Value("${ticket.create}")
    private String sqlTicketCreate;

    @Value("${ticket.findTicket}")
    private String sqlFindTicket;

    @Value("${ticket.byUserId}")
    private String sqlTicketByUserId;

    public void create(final Ticket ticket) {
        jdbcTemplate.update(sqlTicketCreate, ticket.getTicketId(), null == ticket.getUser() ? ticket.getUser() : ticket
                .getUser().getUserId(), ticket.getEvent().getEventId(), ticket.getSeatNumber(), ticket.getPrice());
    }

    public List<Ticket> findTicket(final Event event, final Date date) {
        return jdbcTemplate.query(sqlFindTicket, new Object[] { event.getEventId() }, commonRowMapper());
    }

    @Override
    public List<Ticket> getTicketsByUserId(final String userId) {
        return jdbcTemplate.query(sqlTicketByUserId, new Object[] { userId }, commonRowMapper());
    }

    private RowMapper<Ticket> commonRowMapper() {
        return new RowMapper<Ticket>() {
            public Ticket mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
                Ticket ticket = new Ticket();
                ticket.setTicketId(resultSet.getString(1));
                ticket.setSeatNumber(resultSet.getInt(2));
                ticket.setPrice(resultSet.getDouble(3));
                User user = new User();
                user.setUserId(resultSet.getString(4));
                user.setName(resultSet.getString(5));
                user.setSurname(resultSet.getString(6));
                user.setBirthday(resultSet.getDate(7));
                user.setEmail(resultSet.getString(8));
                Event event = new Event();
                event.setEventId(resultSet.getString(9));
                event.setName(resultSet.getString(10));
                event.setPrice(resultSet.getDouble(11));
                event.setRating(Rating.valueOf(resultSet.getString(12)));
                event.setStartTime(resultSet.getDate(13));
                event.setEndTime(resultSet.getDate(14));
                Auditorium auditorium = new Auditorium();
                auditorium.setAuditoriumId(resultSet.getString(15));
                auditorium.setName(resultSet.getString(16));
                auditorium.setSeats(resultSet.getInt(17));
                String[] vipSeatsString = resultSet.getString(18).split(AuditoriumDaoImpl.SEPARATOR);
                List<Integer> vipSeatsInteger = Lists.newArrayListWithCapacity(vipSeatsString.length);
                for (String vipSeat : vipSeatsString) {
                    vipSeatsInteger.add(Integer.valueOf(vipSeat));
                }
                auditorium.setVipSeats(vipSeatsInteger);
                event.setAuditorium(auditorium);
                ticket.setUser(user);
                ticket.setEvent(event);
                return ticket;
            }
        };
    }
}
