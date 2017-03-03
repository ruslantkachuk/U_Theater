package com.epam.university.service.unittest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.university.dao.AuditoriumDao;
import com.epam.university.entity.Auditorium;
import com.epam.university.service.AuditoriumService;
import com.epam.university.service.impl.AuditoriumServiceImpl;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class AuditoriumServiceImplTest {
    private static final String LIST_AUDITORIUMS_MESSAGE = "Lists of auditoriums are not equals";
    private static final String LIST_AUDITORIUMS_NULL_MESSAGE = "List is null";
    private static final String NUMBER_SEATS_MESSAGE = "Number seats are not equals";

    @Mock
    private AuditoriumDao auditoriumDao;

    @InjectMocks
    private final AuditoriumService auditoriumService = new AuditoriumServiceImpl();

    @Test
    public void shouldReturnListAuditoriums() {
        // given
        Auditorium auditorium = new Auditorium();
        List<Auditorium> listAuditoriums = Lists.newArrayList(auditorium);
        given(auditoriumDao.getAll()).willReturn(listAuditoriums);
        // when
        List<Auditorium> resultListAuditoriums = auditoriumService.getAuditoriums();
        // then
        verify(auditoriumDao).getAll();
        assertThat(LIST_AUDITORIUMS_MESSAGE, resultListAuditoriums.size(), is(listAuditoriums.size()));
        assertThat(LIST_AUDITORIUMS_MESSAGE, resultListAuditoriums, hasItem(auditorium));
    }

    @Test
    public void shouldNotReturnListAuditoriums() {
        // given
        given(auditoriumDao.getAll()).willReturn(null);
        // when
        List<Auditorium> resultListAuditoriums = auditoriumService.getAuditoriums();
        // then
        verify(auditoriumDao).getAll();
        assertThat(LIST_AUDITORIUMS_NULL_MESSAGE, resultListAuditoriums, nullValue());
    }

    @Test
    public void shouldReturnSeatsNumber() {
        // given
        Auditorium auditorium = new Auditorium();
        auditorium.setSeats(Integer.valueOf(100));
        // when
        Integer resultSeats = auditoriumService.getSeatsNumber(auditorium);
        // then
        assertThat(NUMBER_SEATS_MESSAGE, resultSeats, equalTo(Integer.valueOf(100)));
    }

    @Test
    public void shouldReturnListVipSeats() {
        // given
        Auditorium auditorium = new Auditorium();
        List<Integer> vipSeats = Lists.newArrayList(25, 30, 35);
        auditorium.setVipSeats(vipSeats);
        // when
        List<Integer> resultListVipSeats = auditoriumService.getVipSeats(auditorium);
        // then
        assertThat(NUMBER_SEATS_MESSAGE, resultListVipSeats, equalTo(vipSeats));
    }
}
