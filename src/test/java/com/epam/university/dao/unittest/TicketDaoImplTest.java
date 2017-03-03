package com.epam.university.dao.unittest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.epam.university.dao.TicketDao;
import com.epam.university.dao.impl.TicketDaoImpl;
import com.epam.university.entity.Event;
import com.epam.university.entity.Ticket;
import com.epam.university.entity.User;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class TicketDaoImplTest {
    private static final String LIST_TICKETS_MESSAGE = "Lists of tickets are not equals";

    private static final String TICKET_ID = "ticketId";
    private static final String TICKET_EVENT_ID = "ticketEventId";
    private static final String TICKET_USER_ID = "ticketUserId";
    private static final Double TICKET_PRICE = 100d;
    private static final Integer TICKET_SEAT_NUMBER = 1;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private final TicketDao ticketDao = new TicketDaoImpl();

    @Test
    public void shouldCreateTicket() {
        // given
        Ticket ticket = new Ticket();
        ticket.setTicketId(TICKET_ID);
        ticket.setPrice(TICKET_PRICE);
        ticket.setSeatNumber(TICKET_SEAT_NUMBER);
        Event event = new Event();
        event.setEventId(TICKET_EVENT_ID);
        ticket.setEvent(event);
        User user = new User();
        user.setUserId(TICKET_USER_ID);
        ticket.setUser(user);
        // when
        ticketDao.create(ticket);
        // then
        verify(jdbcTemplate).update(anyString(), eq(TICKET_ID), eq(TICKET_USER_ID), eq(TICKET_EVENT_ID),
                eq(TICKET_SEAT_NUMBER), eq(TICKET_PRICE));
    }

    @Test
    public void shouldReturnListOfTickets() {
        // given
        Event event = new Event();
        event.setEventId(TICKET_EVENT_ID);
        List<Ticket> listTicket = Lists.newArrayList(new Ticket());
        given(jdbcTemplate.query(anyString(), eq(new Object[] { TICKET_EVENT_ID }), any(RowMapper.class))).willReturn(
                listTicket);
        // when
        List<Ticket> resultListTicket = ticketDao.findTicket(event, null);
        // then
        verify(jdbcTemplate).query(anyString(), eq(new Object[] { TICKET_EVENT_ID }), any(RowMapper.class));
        assertThat(LIST_TICKETS_MESSAGE, resultListTicket, is(listTicket));
    }

    @Test
    public void shouldReturnListOfTicketsByUserId() {
        // given
        List<Ticket> listTicket = Lists.newArrayList(new Ticket());
        given(jdbcTemplate.query(anyString(), eq(new Object[] { TICKET_USER_ID }), any(RowMapper.class))).willReturn(
                listTicket);
        // when
        List<Ticket> resultTickets = ticketDao.getTicketsByUserId(TICKET_USER_ID);
        // then
        verify(jdbcTemplate).query(anyString(), eq(new Object[] { TICKET_USER_ID }), any(RowMapper.class));
        assertThat(LIST_TICKETS_MESSAGE, resultTickets, is(listTicket));
    }

}
