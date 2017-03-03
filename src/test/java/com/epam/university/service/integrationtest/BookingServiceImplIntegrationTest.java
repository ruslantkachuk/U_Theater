package com.epam.university.service.integrationtest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.university.entity.Event;
import com.epam.university.entity.Ticket;
import com.epam.university.entity.User;
import com.epam.university.service.BookingService;
import com.epam.university.service.EventService;
import com.epam.university.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring.xml", "/spring-test.xml" })
public class BookingServiceImplIntegrationTest {

    private static final String PRICE_MESSAGE = "Prices are not equals";
    private static final String LIST_TICKETS_HAS_NEW_TICKET_MESSAGE = "List of tickets has not new ticket";
    private static final String TICKET_HAS_NOT_USER_MESSAGE = "Ticket has not user";
    private static final String SIZE_LISTS_TICKETS_FOR_EVENT_MESSAGE = "Sizes of tickets lists for event";

    private static final String USER1_ID_FIELD = "user1Id";
    private static final String USER2_ID_FIELD = "user2Id";
    private static final String USER3_ID_FIELD = "user3Id";
    private static final String EVENT1_NAME = "event1Name";
    private static final String EVENT2_NAME = "event2Name";
    private static final String EVENT3_NAME = "event3Name";

    private static final String TICKET_ID = "ticketId";
    private static final Integer TICKET_SEAT_NUMBER = 20;
    private static final Double TICKET_PRICE = 100d;

    @Resource
    private BookingService bookingService;

    @Resource
    private UserService userService;

    @Resource
    private EventService eventService;

    private Ticket ticket;

    private User user1;

    private User user2;

    private User user3;

    private Event event1;

    private Event event2;

    private Event event3;

    @Before
    public void setUp() {
        // given
        user1 = userService.getUserById(USER1_ID_FIELD);
        user2 = userService.getUserById(USER2_ID_FIELD);
        user3 = userService.getUserById(USER3_ID_FIELD);
        event1 = eventService.getEventByName(EVENT1_NAME);
        event2 = eventService.getEventByName(EVENT2_NAME);
        event3 = eventService.getEventByName(EVENT3_NAME);

        ticket = new Ticket();
        ticket.setTicketId(TICKET_ID);
        ticket.setSeatNumber(TICKET_SEAT_NUMBER);
        ticket.setPrice(TICKET_PRICE);
        ticket.setEvent(event3);
    }

    @Test
    public void checkTicketPriceWithHighEventRatingAndTenthTicketDiscount() {
        // when
        Double price = bookingService.getTicketPrice(event1, event1.getStartTime(), event1.getStartTime(),
                Integer.valueOf(10), user3);
        // then
        assertThat(PRICE_MESSAGE, price, is(18d));
    }

    @Test
    public void checkTicketPriceWithMidEventRatingAndWithoutDiscount() {
        // when
        Double price = bookingService.getTicketPrice(event2, event2.getStartTime(), event2.getStartTime(),
                Integer.valueOf(10), user2);
        // then
        assertThat(PRICE_MESSAGE, price, is(50d));
    }

    @Test
    public void checkBookTicket() {
        // when
        ticket.setTicketId(TICKET_ID + "USER1_TEST");
        bookingService.bookTicket(user1, ticket);
        // then
        assertThat(LIST_TICKETS_HAS_NEW_TICKET_MESSAGE, userService.getBookedTickets(user1).size(), is(2));
    }

    @Test
    public void checkBookTicketForNullUser() {
        // given
        ticket.setTicketId(TICKET_ID + "NULL_USER");
        // when
        bookingService.bookTicket(null, ticket);
        // then
        assertThat(TICKET_HAS_NOT_USER_MESSAGE, ticket.getUser(), nullValue());
    }

    @Test
    public void checkReturnListTicketsForEvent() {
        // when
        List<Ticket> resultListTicketsForEvent = bookingService.getTicketsForEvent(event2, new Date());
        // then
        assertThat(SIZE_LISTS_TICKETS_FOR_EVENT_MESSAGE, resultListTicketsForEvent.size(), is(1));
    }

}
