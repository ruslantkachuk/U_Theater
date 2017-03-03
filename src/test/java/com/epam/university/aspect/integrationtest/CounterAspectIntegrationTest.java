package com.epam.university.aspect.integrationtest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.university.aspect.CounterAspect;
import com.epam.university.dao.CounterDao;
import com.epam.university.entity.Counter;
import com.epam.university.entity.Event;
import com.epam.university.entity.Ticket;
import com.epam.university.entity.User;
import com.epam.university.service.BookingService;
import com.epam.university.service.EventService;
import com.epam.university.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring.xml", "/spring-test.xml" })
public class CounterAspectIntegrationTest {

    private static final String COUNT_EVENT_ACCESS_BY_NAME_MESSAGE = "Counts of event access by name are not equals";
    private static final String COUNT_EVENT_PRICE_QUERIED = "Counts of event price queried are not equals";
    private static final String COUNT_EVENT_TICKET_BOOKED = "Counts of event ticket booked are not equals";

    private static final String USER3_ID_FIELD = "user3Id";
    private static final String EVENT1_NAME = "event1Name";

    private static final String TICKET_ID = "ticketId";
    private static final Integer TICKET_SEAT_NUMBER = 20;
    private static final Double TICKET_PRICE = 100d;

    @Resource
    private UserService userService;

    @Resource
    private EventService eventService;

    @Resource
    private BookingService bookingService;

    private Event event1;

    private User user3;

    private Ticket ticket;

    @Resource
    private CounterDao counterDao;

    @Before
    public void setUp() {
        user3 = userService.getUserById(USER3_ID_FIELD);
        event1 = eventService.getEventByName(EVENT1_NAME);

        ticket = new Ticket();
        ticket.setTicketId(TICKET_ID);
        ticket.setSeatNumber(TICKET_SEAT_NUMBER);
        ticket.setPrice(TICKET_PRICE);
        ticket.setEvent(event1);
    }

    @Test
    public void checkCountEventAccessedByName() {
        // given
        Integer eventCount = Integer.valueOf(0);
        try {
            Counter resultCounter = counterDao.getCounterByIdAndEntityIdAndEntityType(
                    CounterAspect.COUNTER_EVENT_BY_NAME_ID + event1.getEventId(), event1.getEventId(), event1
                            .getClass().getSimpleName());
            eventCount = resultCounter.getEntityCount();
        } catch (EmptyResultDataAccessException exception) {
        }
        // when
        eventService.getEventByName(event1.getName());
        // then
        assertThat(
                COUNT_EVENT_ACCESS_BY_NAME_MESSAGE,
                counterDao.getCounterByIdAndEntityIdAndEntityType(
                        CounterAspect.COUNTER_EVENT_BY_NAME_ID + event1.getEventId(), event1.getEventId(),
                        event1.getClass().getSimpleName()).getEntityCount(), is(eventCount + 1));
    }

    @Test
    public void checkCountEventPriceQueried() {
        // given
        Integer eventCount = Integer.valueOf(0);
        try {
            Counter resultCounter = counterDao.getCounterByIdAndEntityIdAndEntityType(
                    CounterAspect.COUNTER_EVENT_TICKET_PRICE_ID + event1.getEventId(), event1.getEventId(), event1
                            .getClass().getSimpleName());
            eventCount = resultCounter.getEntityCount();
        } catch (EmptyResultDataAccessException exception) {
        }
        // when
        bookingService.getTicketPrice(event1, new Date(), new Date(), Integer.valueOf(1), user3);
        // then
        assertThat(
                COUNT_EVENT_PRICE_QUERIED,
                counterDao.getCounterByIdAndEntityIdAndEntityType(
                        CounterAspect.COUNTER_EVENT_TICKET_PRICE_ID + event1.getEventId(), event1.getEventId(),
                        event1.getClass().getSimpleName()).getEntityCount(), is(eventCount + 1));
    }

    @Test
    public void checkCountEventTicketBooked() {
        // given
        Integer eventCount = Integer.valueOf(0);
        try {
            Counter resultCounter = counterDao.getCounterByIdAndEntityIdAndEntityType(
                    CounterAspect.COUNTER_EVENT_BOOK_TICKET_ID + ticket.getEvent().getEventId(), ticket.getEvent()
                            .getEventId(), ticket.getEvent().getClass().getSimpleName());
            eventCount = resultCounter.getEntityCount();
        } catch (EmptyResultDataAccessException exception) {
        }
        // when
        bookingService.bookTicket(null, ticket);
        // then
        assertThat(
                COUNT_EVENT_TICKET_BOOKED,
                counterDao.getCounterByIdAndEntityIdAndEntityType(
                        CounterAspect.COUNTER_EVENT_BOOK_TICKET_ID + ticket.getEvent().getEventId(),
                        ticket.getEvent().getEventId(), ticket.getEvent().getClass().getSimpleName()).getEntityCount(),
                is(eventCount + 1));
    }
}
