package com.epam.university.dao.unittest;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.epam.university.dao.CounterDao;
import com.epam.university.dao.impl.CounterDaoImpl;
import com.epam.university.entity.Counter;

@RunWith(MockitoJUnitRunner.class)
public class CounterDaoImplTest {

    private static final String COUNTER_MESSAGE = "Counters are not equals";
    private static final String COUNTER_ID = "counterId";
    private static final String COUNTER_EVENT_ID = "eventId";
    private static final String COUNTER_EVENT_TYPE = "eventType";
    private static final String COUNTER_EVENT_BY_NAME_ID = "eventByNameId";
    private static final Integer COUNTER_EVENT_COUNT = 1;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private final CounterDao counterDao = new CounterDaoImpl();

    @Test
    public void shouldCreateCounter() {
        // given
        Counter counter = new Counter();
        counter.setCounterId(COUNTER_ID);
        counter.setEntityId(COUNTER_EVENT_ID);
        counter.setEntityType(COUNTER_EVENT_TYPE);
        counter.setEntityCount(COUNTER_EVENT_COUNT);
        // when
        counterDao.create(counter);
        // then
        verify(jdbcTemplate).update(anyString(), eq(COUNTER_ID), eq(COUNTER_EVENT_ID), eq(COUNTER_EVENT_TYPE),
                eq(COUNTER_EVENT_COUNT));
    }

    @Test
    public void shouldReturnCounterByIdAndEntityIdAndEntityType() {
        // given
        Counter counter = new Counter();
        given(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).willReturn(counter);
        // when
        Counter resultCounter = counterDao.getCounterByIdAndEntityIdAndEntityType(COUNTER_EVENT_BY_NAME_ID,
                COUNTER_EVENT_ID, COUNTER_EVENT_TYPE);
        // then
        assertThat(COUNTER_MESSAGE, resultCounter, is(equalTo(counter)));
    }

    @Test
    public void shouldNotReturnCounterByIdAndTypeEntity() {
        // when
        Counter resultCounter = counterDao.getCounterByIdAndEntityIdAndEntityType(COUNTER_EVENT_BY_NAME_ID,
                COUNTER_EVENT_ID, COUNTER_EVENT_TYPE);
        // then
        assertThat(COUNTER_MESSAGE, resultCounter, nullValue());
    }
}