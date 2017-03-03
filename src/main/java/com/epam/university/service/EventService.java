package com.epam.university.service;

import java.util.Date;
import java.util.List;

import com.epam.university.entity.Auditorium;
import com.epam.university.entity.Event;

public interface EventService {
    void create(Event event);

    void remove(Event event);

    Event getEventByName(String eventName);

    List<Event> getAll();

    List<Event> getForDateRange(Date fromDate, Date toDate);

    void assignAuditorium(Event event, Auditorium auditorium, Date date);
}
