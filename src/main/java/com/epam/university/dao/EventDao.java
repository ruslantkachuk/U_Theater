package com.epam.university.dao;

import java.util.Date;
import java.util.List;

import com.epam.university.entity.Event;

public interface EventDao {
    void create(Event event);

    void update(Event event);

    void remove(String eventId);

    Event getEventByName(String eventName);

    List<Event> getAll();

    List<Event> getEventsForDateRange(Date fromDate, Date toDate);
}
