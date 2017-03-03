package com.epam.university.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.epam.university.dao.EventDao;
import com.epam.university.entity.Auditorium;
import com.epam.university.entity.Event;
import com.epam.university.service.EventService;

public class EventServiceImpl implements EventService {
    @Resource
    private EventDao eventDao;

    public void create(final Event event) {
        eventDao.create(event);
    }

    public void remove(final Event event) {
        eventDao.remove(event.getEventId());
    }

    public Event getEventByName(final String eventName) {
        return eventDao.getEventByName(eventName);
    }

    public List<Event> getAll() {
        return eventDao.getAll();
    }

    public List<Event> getForDateRange(final Date fromDate, final Date toDate) {
        return eventDao.getEventsForDateRange(fromDate, toDate);
    }

    public void assignAuditorium(final Event event, final Auditorium auditorium, final Date date) {
        event.setAuditorium(auditorium);
        eventDao.update(event);
    }
}
