package com.epam.university.service.unittest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.university.dao.EventDao;
import com.epam.university.entity.Auditorium;
import com.epam.university.entity.Event;
import com.epam.university.service.EventService;
import com.epam.university.service.impl.EventServiceImpl;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class EventServiceImplTest {
    private static final String EVENT_MESSAGE = "Events are not equals";
    private static final String EVENT_NULL_MESSAGE = "Event is null";
    private static final String EVENT_AUDITORIUM_MESSAGE = "Event's auditorium is null";
    private static final String LIST_EVENTS_MESSAGE = "Lists are not equals";

    private static final String EVENT_ID_FIELD_1 = "eventId1";
    private static final String EVENT_NAME_FIELD_1 = "eventName1";

    @Mock
    private EventDao eventDao;

    @InjectMocks
    private final EventService eventService = new EventServiceImpl();

    @Test
    public void shouldCreateEvent() {
        // given
        Event event = new Event();
        // when
        eventService.create(event);
        // then
        verify(eventDao).create(event);
    }

    @Test
    public void shouldRemoveEvent() {
        // given
        Event event = new Event();
        event.setEventId(EVENT_ID_FIELD_1);
        // when
        eventService.remove(event);
        // then
        verify(eventDao).remove(EVENT_ID_FIELD_1);
    }

    @Test
    public void shouldReturnUserByName() {
        // given
        Event event = new Event();
        event.setName(EVENT_NAME_FIELD_1);
        given(eventDao.getEventByName(EVENT_NAME_FIELD_1)).willReturn(event);
        // when
        Event resultEvent = eventService.getEventByName(EVENT_NAME_FIELD_1);
        // then
        verify(eventDao).getEventByName(EVENT_NAME_FIELD_1);
        assertThat(EVENT_MESSAGE, resultEvent, is(equalTo(event)));
    }

    @Test
    public void shouldNotReturnUserByName() {
        // given
        given(eventDao.getEventByName(EVENT_NAME_FIELD_1)).willReturn(null);
        // when
        Event resultEvent = eventService.getEventByName(EVENT_NAME_FIELD_1);
        // then
        verify(eventDao).getEventByName(EVENT_NAME_FIELD_1);
        assertThat(EVENT_NULL_MESSAGE, resultEvent, nullValue());
    }

    @Test
    public void shouldReturnListEvents() {
        // given
        Event event = new Event();
        List<Event> listEvents = Lists.newArrayList(event);
        given(eventDao.getAll()).willReturn(listEvents);
        // when
        List<Event> resultListEvents = eventService.getAll();
        // then
        verify(eventDao).getAll();
        assertThat(LIST_EVENTS_MESSAGE, resultListEvents.size(), is(listEvents.size()));
        assertThat(LIST_EVENTS_MESSAGE, resultListEvents, hasItem(event));
    }

    @Test
    public void shouldReturnListEventsByDateRange() {
        // given
        Event event = new Event();
        List<Event> listEvents = Lists.newArrayList(event);
        given(eventDao.getEventsForDateRange(any(Date.class), any(Date.class))).willReturn(listEvents);
        // when
        List<Event> resultListEvents = eventService.getForDateRange(new Date(), new Date());
        // then
        verify(eventDao).getEventsForDateRange(any(Date.class), any(Date.class));
        assertThat(LIST_EVENTS_MESSAGE, resultListEvents.size(), is(listEvents.size()));
        assertThat(LIST_EVENTS_MESSAGE, resultListEvents, hasItem(event));
    }

    @Test
    public void shouldAssignAuditorium() {
        // given
        Event event = new Event();
        Auditorium auditorium = new Auditorium();
        // when
        eventService.assignAuditorium(event, auditorium, new Date());
        // then
        verify(eventDao).update(event);
        assertThat(EVENT_AUDITORIUM_MESSAGE, event.getAuditorium(), equalTo(auditorium));
    }

}
