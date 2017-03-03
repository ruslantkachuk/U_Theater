package com.epam.university.dao.unittest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.epam.university.dao.EventDao;
import com.epam.university.dao.impl.EventDaoImpl;
import com.epam.university.entity.Auditorium;
import com.epam.university.entity.Event;
import com.epam.university.entity.Rating;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class EventDaoImplTest {
    private static final String EVENT_MESSAGE = "Events are not equals";
    private static final String LIST_EVENTS_MESSAGE = "Lists of events are not equals";

    private static final String EVENT_ID = "eventId";
    private static final String EVENT_NAME = "eventName";
    private static final String EVENT_AUDITORIUM_ID = "auditoriumId";
    private static final Double EVENT_PRICE = 100d;
    private static final Date EVENT_START_TIME = new Date();
    private static final Date EVENT_END_TIME = new Date();

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private final EventDao eventDao = new EventDaoImpl();

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
    }

    @Test
    public void shouldReturnAllEvents() {
        // given
        List<Event> listEvent = Lists.newArrayList(new Event());
        given(jdbcTemplate.query(anyString(), any(RowMapper.class))).willReturn(listEvent);
        // when
        List<Event> resultListEvent = eventDao.getAll();
        // then
        assertThat(LIST_EVENTS_MESSAGE, resultListEvent, is(listEvent));
    }

    @Test
    public void shouldCreateEvent() {
        // given
        Auditorium auditorium = new Auditorium();
        auditorium.setAuditoriumId(EVENT_AUDITORIUM_ID);
        event.setAuditorium(auditorium);
        // when
        eventDao.create(event);
        // then
        verify(jdbcTemplate).update(anyString(), eq(EVENT_ID), eq(EVENT_NAME), eq(EVENT_PRICE),
                eq(Rating.HIGH.toString()), eq(EVENT_START_TIME), eq(EVENT_END_TIME), eq(EVENT_AUDITORIUM_ID));
    }

    @Test
    public void shouldUpdateEvent() {
        // given
        Auditorium auditorium = new Auditorium();
        auditorium.setAuditoriumId(EVENT_AUDITORIUM_ID);
        event.setAuditorium(auditorium);
        // when
        eventDao.update(event);
        // then
        verify(jdbcTemplate).update(anyString(), eq(EVENT_NAME), eq(EVENT_PRICE), eq(Rating.HIGH.toString()),
                eq(EVENT_START_TIME), eq(EVENT_END_TIME), eq(EVENT_AUDITORIUM_ID), eq(EVENT_ID));
    }

    @Test
    public void shouldRemoveEvent() {
        // when
        eventDao.remove(EVENT_ID);
        // then
        verify(jdbcTemplate).update(anyString(), eq(EVENT_ID));
    }

    @Test
    public void shouldReturnEventByName() {
        // given
        Event eventField = new Event();
        given(jdbcTemplate.queryForObject(anyString(), eq(new Object[] { EVENT_NAME }), any(RowMapper.class)))
                .willReturn(eventField);
        // when
        Event resultEvent = eventDao.getEventByName(EVENT_NAME);
        // then
        verify(jdbcTemplate).queryForObject(anyString(), eq(new Object[] { EVENT_NAME }), any(RowMapper.class));
        assertThat(EVENT_MESSAGE, resultEvent, is(equalTo(eventField)));
    }

    @Test
    public void shouldReturnListOfEvents() {
        // given
        DateTime startEventRange = new DateTime(2016, 2, 12, 0, 0);
        DateTime endEventRange = new DateTime(2016, 2, 13, 0, 0);
        List<Event> listEvents = Lists.newArrayList(new Event());
        given(
                jdbcTemplate.query(anyString(), eq(new Object[] { startEventRange.toDate(), endEventRange.toDate() }),
                        any(RowMapper.class))).willReturn(listEvents);
        // when
        List<Event> resultListEvents = eventDao.getEventsForDateRange(startEventRange.toDate(), endEventRange.toDate());
        // then
        verify(jdbcTemplate).query(anyString(), eq(new Object[] { startEventRange.toDate(), endEventRange.toDate() }),
                any(RowMapper.class));
        assertThat(LIST_EVENTS_MESSAGE, resultListEvents, is(listEvents));
    }
}
