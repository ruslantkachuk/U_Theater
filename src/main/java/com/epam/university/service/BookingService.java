package com.epam.university.service;

import java.util.Date;
import java.util.List;

import com.epam.university.entity.Event;
import com.epam.university.entity.Ticket;
import com.epam.university.entity.User;

public interface BookingService {
    Double getTicketPrice(Event event, Date date, Date time, Integer seat, User user);

    void bookTicket(User user, Ticket ticket);

    List<Ticket> getTicketsForEvent(Event event, Date date);
}
