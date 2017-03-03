package com.epam.university.service.integrationtest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.university.entity.Auditorium;
import com.epam.university.service.AuditoriumService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring.xml", "/spring-test.xml" })
public class AuditoriumServiceImplIntegrationTest {

    @Value("${auditoriumSmall.name}")
    private String AUDITORIUM_SMALL_NAME;
    @Value("${auditoriumBig.name}")
    private String AUDITORIUM_BIG_NAME;
    @Value("${auditoriumSmall.seats}")
    private Integer AUDITORIUM_SMALL_SEATS;
    @Value("${auditoriumBig.seats}")
    private Integer AUDITORIUM_BIG_SEATS;
    @Value("#{T(java.util.Arrays).asList('${auditoriumSmall.vipSeats}')}")
    private List<Integer> AUDITORIUM_SMALL_VIP_SEATS;
    @Value("#{T(java.util.Arrays).asList('${auditoriumBig.vipSeats}')}")
    private List<Integer> AUDITORIUM_BIG_VIP_SEATS;

    private static final String AUDITORIUM_NAME_MESSAGE = "Name of auditorium is not equals";
    private static final String SIZE_LIST_AUDITORIUMS_SIZE_MESSAGE = "Sizes of list auditoriums are not equals";
    private static final String SEAT_NUMBER_OF_AUDITORIUMS_MESSAGE = "Seat numbers of auditorium are not equals";
    private static final String LIST_VIP_SEAT_OF_AUDITORIUMS_MESSAGE = "Lists of vip seat of auditorium are not equals";

    @Resource
    private AuditoriumService auditoriumService;

    @Test
    public void checkListAuditoriums() {
        // when
        List<Auditorium> resultListAuditoriums = auditoriumService.getAuditoriums();
        // then
        assertThat(SIZE_LIST_AUDITORIUMS_SIZE_MESSAGE, resultListAuditoriums.size(), is(2));
        assertThat(AUDITORIUM_NAME_MESSAGE, resultListAuditoriums.get(0).getName(), is(AUDITORIUM_BIG_NAME));
        assertThat(AUDITORIUM_NAME_MESSAGE, resultListAuditoriums.get(1).getName(), is(AUDITORIUM_SMALL_NAME));
    }

    @Test
    public void checkSeatsNumberOfAuditoriums() {
        // when
        Integer resultSeatsNumberBigAuditorium = auditoriumService.getSeatsNumber(auditoriumService.getAuditoriums()
                .get(0));
        Integer resultSeatsNumberSmallAuditorium = auditoriumService.getSeatsNumber(auditoriumService.getAuditoriums()
                .get(1));
        // then
        assertThat(SEAT_NUMBER_OF_AUDITORIUMS_MESSAGE, resultSeatsNumberBigAuditorium, is(AUDITORIUM_BIG_SEATS));
        assertThat(SEAT_NUMBER_OF_AUDITORIUMS_MESSAGE, resultSeatsNumberSmallAuditorium, is(AUDITORIUM_SMALL_SEATS));
    }

    @Test
    public void checkVipSeatsOfAuditoriums() {
        // when
        List<Integer> resultListVipSeatsNumberBigAuditorium = auditoriumService.getVipSeats(auditoriumService
                .getAuditoriums().get(0));
        List<Integer> resultListVipSeatsNumberSmallAuditorium = auditoriumService.getVipSeats(auditoriumService
                .getAuditoriums().get(1));
        // then
        assertThat(LIST_VIP_SEAT_OF_AUDITORIUMS_MESSAGE, resultListVipSeatsNumberBigAuditorium,
                is(AUDITORIUM_BIG_VIP_SEATS));
        assertThat(LIST_VIP_SEAT_OF_AUDITORIUMS_MESSAGE, resultListVipSeatsNumberSmallAuditorium,
                is(AUDITORIUM_SMALL_VIP_SEATS));
    }
}
