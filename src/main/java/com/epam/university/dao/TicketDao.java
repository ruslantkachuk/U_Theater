package com.epam.university.dao;

import java.util.Date;
import java.util.List;

import com.epam.university.entity.Event;
import com.epam.university.entity.Ticket;

public interface TicketDao {
    void create(Ticket ticket);

    List<Ticket> findTicket(Event event, Date date);

    List<Ticket> getTicketsByUserId(String userId);

}
