package com.epam.university.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.epam.university.dao.TicketDao;
import com.epam.university.entity.Event;
import com.epam.university.entity.Rating;
import com.epam.university.entity.Ticket;
import com.epam.university.entity.User;
import com.epam.university.service.AuditoriumService;
import com.epam.university.service.BookingService;
import com.epam.university.service.DiscountService;

public class BookingServiceImpl implements BookingService {

    @Resource
    private DiscountService discountService;

    @Resource
    private AuditoriumService auditoriumService;

    @Resource
    private TicketDao ticketDao;

    @Value("${coefficient.no}")
    private Double coefficientNo;

    @Value("${coefficient.highRating}")
    private Double coefficientHighrating;

    @Value("${coefficient.vipSeats}")
    private Double coefficientVipseats;

    @Value("${coefficient.divider}")
    private Integer coefficientDivider;

    public Double getTicketPrice(final Event event, final Date date, final Date time, final Integer seat,
            final User user) {
        int percentDiscount = discountService.getDiscount(user, event, date);
        double coefficientVipSeats = auditoriumService.getVipSeats(event.getAuditorium()).contains(seat) ? coefficientVipseats
                : coefficientNo;
        double coefficientHighRating = StringUtils.equals(Rating.HIGH.name(), event.getRating().name()) ? coefficientHighrating
                : coefficientNo;
        double price = event.getPrice() * coefficientVipSeats * coefficientHighRating;
        return price - price * percentDiscount / coefficientDivider;
    }

    public void bookTicket(final User user, final Ticket ticket) {
        if (null != user) {
            ticket.setUser(user);
        }
        ticketDao.create(ticket);
    }

    public List<Ticket> getTicketsForEvent(final Event event, final Date date) {
        return ticketDao.findTicket(event, date);
    }
}
