package com.epam.university.aspect.integrationtest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.university.aspect.LuckyWinnerAspect;
import com.epam.university.dao.CounterDao;
import com.epam.university.dao.TicketDao;
import com.epam.university.entity.Event;
import com.epam.university.entity.Ticket;
import com.epam.university.entity.User;
import com.epam.university.service.BookingService;
import com.epam.university.service.EventService;
import com.epam.university.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring.xml", "/spring-test.xml" })
public class LuckyWinnerAspectIntegrationTest {

    private static final String PRICE_TICKETS_MESSAGE = "Prices of tickets are not equals";

    private static final String USER4_ID = "user4Id";
    private static final String EVENT4_NAME = "event4Name";
    private static final String TICKET_ID = "ticketIdLuckyWinnerAspect";
    private static final Integer TICKET_SEAT_NUMBER = 20;
    private static final Double TICKET_PRICE = 100d;

    @Resource
    private EventService eventService;

    @Resource
    private UserService userService;

    @Resource
    private BookingService bookingService;

    @Resource
    private CounterDao counterDao;

    @Resource
    private TicketDao ticketDao;

    private Ticket ticket;

    private Event event4;

    private User user4;

    @Before
    public void setUp() {
        event4 = eventService.getEventByName(EVENT4_NAME);
        user4 = userService.getUserById(USER4_ID);
        ticket = new Ticket();
        ticket.setTicketId(TICKET_ID);
        ticket.setSeatNumber(TICKET_SEAT_NUMBER);
        ticket.setPrice(TICKET_PRICE);
        ticket.setEvent(event4);
    }

    @Test
    public void checkCountEventPriceQueried() {
        // when
        bookingService.bookTicket(user4, ticket);
        // then
        try {
            counterDao.getCounterByIdAndEntityIdAndEntityType(
                    LuckyWinnerAspect.LUCKY_WINNER_TICKET + ticket.getTicketId(), ticket.getTicketId(), ticket
                            .getClass().getSimpleName());
            assertThat(PRICE_TICKETS_MESSAGE, ticketDao.findTicket(event4, null).get(0).getPrice(),
                    equalTo(Double.valueOf(0)));
        } catch (EmptyResultDataAccessException exception) {
        }
    }
}
