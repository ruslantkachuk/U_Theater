package com.epam.university.service.unittest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.epam.university.dao.TicketDao;
import com.epam.university.entity.*;
import com.epam.university.service.AuditoriumService;
import com.epam.university.service.BookingService;
import com.epam.university.service.DiscountService;
import com.epam.university.service.impl.BookingServiceImpl;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceImplTest {

    private static final String TICKET_PRICE_MESSAGE = "Price of tickets are not equals";
    private static final String LIST_TICKET_MESSAGE = "List of tickets are not equals";
    private static final String TICKET_USER_MESSAGE = "Tickets user are not equals";

    private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("price");

    @Mock
    private DiscountService discountService;

    @Mock
    private AuditoriumService auditoriumService;

    @Mock
    private TicketDao ticketDao;

    @InjectMocks
    private final BookingService bookingService = new BookingServiceImpl();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(bookingService, "coefficientNo",
                Double.valueOf(RESOURCE.getString("coefficient.no")));
        ReflectionTestUtils.setField(bookingService, "coefficientHighrating",
                Double.valueOf(RESOURCE.getString("coefficient.highRating")));
        ReflectionTestUtils.setField(bookingService, "coefficientVipseats",
                Double.valueOf(RESOURCE.getString("coefficient.vipSeats")));
        ReflectionTestUtils.setField(bookingService, "coefficientDivider",
                Integer.valueOf(RESOURCE.getString("coefficient.divider")));
    }

    @Test
    public void shouldReturnTicketPrice() {
        // given
        Event event = new Event();
        event.setRating(Rating.HIGH);
        event.setPrice(100d);
        Integer seat = Integer.valueOf(1);
        given(discountService.getDiscount(any(User.class), any(Event.class), any(Date.class))).willReturn(5);
        given(auditoriumService.getVipSeats(any(Auditorium.class))).willReturn(Lists.<Integer>newArrayList(seat));
        // when
        Double ticketPrice = bookingService.getTicketPrice(event, new Date(), new Date(), seat, new User());
        // then
        verify(discountService).getDiscount(any(User.class), any(Event.class), any(Date.class));
        verify(auditoriumService).getVipSeats(any(Auditorium.class));
        assertThat(TICKET_PRICE_MESSAGE, ticketPrice, equalTo(228d));
    }

    @Test
    public void shouldBookTicket() {
        // given
        User user = new User();
        Ticket ticket = new Ticket();
        // when
        bookingService.bookTicket(user, ticket);
        // then
        verify(ticketDao).create(ticket);
        assertThat(TICKET_USER_MESSAGE, ticket.getUser(), is(user));
    }

    @Test
    public void shouldBookTicketForGuest() {
        // given
        Ticket ticket = new Ticket();
        // when
        bookingService.bookTicket(null, ticket);
        // then
        verify(ticketDao).create(ticket);
    }

    @Test
    public void shouldReturnTicketsForEvent() {
        // given
        List<Ticket> listTickets = Lists.<Ticket>newArrayList(new Ticket());
        given(ticketDao.findTicket(any(Event.class), any(Date.class))).willReturn(listTickets);
        // when
        List<Ticket> resultListTicket = bookingService.getTicketsForEvent(any(Event.class), any(Date.class));
        // then
        verify(ticketDao).findTicket(any(Event.class), any(Date.class));
        assertThat(LIST_TICKET_MESSAGE, resultListTicket, equalTo(listTickets));
    }

}
