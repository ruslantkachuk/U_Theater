package com.epam.university.dao.unittest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.epam.university.dao.AuditoriumDao;
import com.epam.university.dao.impl.AuditoriumDaoImpl;
import com.epam.university.entity.Auditorium;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class AuditoriumDaoImplTest {
    private static final String LIST_AUDITORIUMS_MESSAGE = "Lists of auditoriums are not equals";

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private final AuditoriumDao auditoriumDao = new AuditoriumDaoImpl();

    @Test
    public void shouldReturnAllAuditoriums() {
        // given
        List<Auditorium> listAuditorium = Lists.newArrayList(new Auditorium());
        given(jdbcTemplate.query(anyString(), any(RowMapper.class))).willReturn(listAuditorium);
        // when
        List<Auditorium> resultListAuditorium = auditoriumDao.getAll();
        // then
        verify(jdbcTemplate).query(anyString(), any(RowMapper.class));
        assertThat(LIST_AUDITORIUMS_MESSAGE, resultListAuditorium, is(listAuditorium));
    }
}