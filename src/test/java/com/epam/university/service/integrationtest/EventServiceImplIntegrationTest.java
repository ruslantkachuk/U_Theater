package com.epam.university.service.integrationtest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.university.entity.Auditorium;
import com.epam.university.entity.Event;
import com.epam.university.entity.Rating;
import com.epam.university.service.EventService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring.xml", "/spring-test.xml" })
public class EventServiceImplIntegrationTest {

    private static final String EVENT_MESSAGE = "Events are not equals";
    private static final String AUDITORIUMS_MESSAGE = "Auditoriums are not equals";
    private static final String LIST_EVENTS_SIZE_MESSAGE = "List events are not equals";
    private static final String SIZE_LIST_EVENTS_SIZE_MESSAGE = "Sizes of list events are not equals";

    private static final String EVENT1_NAME = "event1Name";
    private static final String EVENT_ID = "eventId";
    private static final String EVENT_NAME = "eventName";
    private static final String EVENT_AUDITORIUM_ID = "Auditorium Small";
    private static final Double EVENT_PRICE = 100d;
    private static final Date EVENT_START_TIME = new Date();
    private static final Date EVENT_END_TIME = new Date();

    @Resource
    private EventService eventService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private Event event;

    @Before
    public void setUp() {
        event = new Event();
        event.setEventId(EVENT_ID);
        event.setName(EVENT_NAME);
        event.setRating(Rating.HIGH);
        event.setPrice(EVENT_PRICE);
        event.setStartTime(EVENT_START_TIME);
        event.setEndTime(EVENT_END_TIME);
        Auditorium auditorium = new Auditorium();
        event.setAuditorium(auditorium);
    }

    @Test
    public void checkCreateEvent() {
        // given
        event.getAuditorium().setAuditoriumId(EVENT_AUDITORIUM_ID);
        // when
        eventService.create(event);
        // then
        assertThat(EVENT_MESSAGE, eventService.getEventByName(EVENT_NAME).getEventId(), is(EVENT_ID));
    }

    @Test
    public void checkRemoveEvent() {
        // when
        eventService.remove(event);
        // then
        exception.expect(EmptyResultDataAccessException.class);
        eventService.getEventByName(EVENT_NAME);
    }

    @Test
    public void checkReturnEventByName() {
        // when
        Event resultEvent = eventService.getEventByName(EVENT1_NAME);
        // then
        assertThat(EVENT_MESSAGE, resultEvent, notNullValue());
    }

    @Test
    public void checkListEvents() {
        // when
        List<Event> resultListEvents = eventService.getAll();
        // then
        assertThat(LIST_EVENTS_SIZE_MESSAGE, resultListEvents, notNullValue());
        assertThat(SIZE_LIST_EVENTS_SIZE_MESSAGE, resultListEvents.size(), is(4));
    }

    @Test
    public void checkReturnListEventsByDateRange() {
        // given
        DateTime startEventRange = new DateTime(2016, 2, 12, 12, 0);
        DateTime endEventRange = new DateTime(2016, 2, 12, 16, 0);
        // when
        List<Event> resultListEvents = eventService.getForDateRange(startEventRange.toDate(), endEventRange.toDate());
        // then
        assertThat(SIZE_LIST_EVENTS_SIZE_MESSAGE, resultListEvents.size(), is(3));
    }

    @Test
    public void checkAssignAuditorium() {
        // given
        Event eventField = eventService.getEventByName(EVENT1_NAME);
        eventField.getAuditorium().setAuditoriumId(EVENT_AUDITORIUM_ID);
        // when
        eventService.assignAuditorium(eventField, eventField.getAuditorium(), new Date());
        // then
        assertThat(AUDITORIUMS_MESSAGE, eventService.getEventByName(EVENT1_NAME).getAuditorium().getAuditoriumId(),
                is(EVENT_AUDITORIUM_ID));
    }
}