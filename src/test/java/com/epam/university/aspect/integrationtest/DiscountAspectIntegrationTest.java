package com.epam.university.aspect.integrationtest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.university.aspect.DiscountAspect;
import com.epam.university.dao.CounterDao;
import com.epam.university.entity.Counter;
import com.epam.university.entity.Event;
import com.epam.university.entity.User;
import com.epam.university.service.DiscountService;
import com.epam.university.service.EventService;
import com.epam.university.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring.xml", "/spring-test.xml" })
public class DiscountAspectIntegrationTest {

    private static final String COUNT_DISCOUNT_WAS_GIVEN_TOTAL_MESSAGE = "Counts of discount are not equals";
    private static final String COUNT_DISCOUNT_TOTAL_FOR_USER_MESSAGE = "Counts of discount for user are not equals";

    private static final String USER1_ID_FIELD = "user1Id";
    private static final String USER3_ID_FIELD = "user3Id";
    private static final String EVENT1_NAME = "event1Name";

    @Resource
    private UserService userService;

    @Resource
    private EventService eventService;

    @Resource
    private CounterDao counterDao;

    @Resource
    private DiscountService discountService;

    private User user1;

    private User user3;

    private Event event1;

    @Before
    public void setUp() {
        user1 = userService.getUserById(USER1_ID_FIELD);
        user3 = userService.getUserById(USER3_ID_FIELD);
        event1 = eventService.getEventByName(EVENT1_NAME);
    }

    @Test
    public void checkCountDiscountWasGivenTotal() {
        // given
        Integer eventCount = Integer.valueOf(0);
        try {
            Counter resultCounter = counterDao.getCounterByIdAndEntityIdAndEntityType(
                    DiscountAspect.DISCOUNT_WAS_GIVEN_TOTAL, "", int.class.getSimpleName());
            eventCount = resultCounter.getEntityCount();
        } catch (EmptyResultDataAccessException exception) {
        }
        // when
        discountService.getDiscount(user3, event1, event1.getStartTime());
        // then
        assertThat(
                COUNT_DISCOUNT_WAS_GIVEN_TOTAL_MESSAGE,
                counterDao.getCounterByIdAndEntityIdAndEntityType(DiscountAspect.DISCOUNT_WAS_GIVEN_TOTAL, "",
                        int.class.getSimpleName()).getEntityCount(), is(eventCount + 1));
    }

    @Test
    public void checkCountDiscountWasGivenTotalForSpecificUser() {
        // given
        Integer eventCount = Integer.valueOf(0);
        try {
            Counter resultCounter = counterDao.getCounterByIdAndEntityIdAndEntityType(
                    DiscountAspect.DISCOUNT_WAS_GIVEN_TOTAL + user1.getUserId(), user1.getUserId(), user1.getClass()
                            .getSimpleName());
            eventCount = resultCounter.getEntityCount();
        } catch (EmptyResultDataAccessException exception) {
        }
        // when
        discountService.getDiscount(user1, event1, event1.getStartTime());
        // then
        assertThat(
                COUNT_DISCOUNT_TOTAL_FOR_USER_MESSAGE,
                counterDao.getCounterByIdAndEntityIdAndEntityType(
                        DiscountAspect.DISCOUNT_WAS_GIVEN_TOTAL + user1.getUserId(), user1.getUserId(),
                        user1.getClass().getSimpleName()).getEntityCount(), is(eventCount + 1));
    }

}
