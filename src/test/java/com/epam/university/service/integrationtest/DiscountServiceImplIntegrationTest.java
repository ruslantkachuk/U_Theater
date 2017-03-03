package com.epam.university.service.integrationtest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.university.entity.Event;
import com.epam.university.entity.User;
import com.epam.university.service.DiscountService;
import com.epam.university.service.EventService;
import com.epam.university.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring.xml", "/spring-test.xml" })
public class DiscountServiceImplIntegrationTest {
    private static final String DISCOUNT_MESSAGE = "Discounts are not equals";

    private static final String USER1_ID_FIELD = "user1Id";
    private static final String USER2_ID_FIELD = "user2Id";
    private static final String USER3_ID_FIELD = "user3Id";
    private static final String EVENT1_NAME = "event1Name";

    @Resource
    private DiscountService discountService;

    @Resource
    private UserService userService;

    @Resource
    private EventService eventService;

    private User user1;

    private User user2;

    private User user3;

    private Event event1;

    @Before
    public void setUp() {
        user1 = userService.getUserById(USER1_ID_FIELD);
        user2 = userService.getUserById(USER2_ID_FIELD);
        user3 = userService.getUserById(USER3_ID_FIELD);
        event1 = eventService.getEventByName(EVENT1_NAME);
    }

    @Test
    public void checkReturnMaxDiscountBirthdayStrategy() {
        // when
        Integer discount = discountService.getDiscount(user1, event1, event1.getStartTime());
        // then
        assertThat(DISCOUNT_MESSAGE, discount, is(5));
    }

    @Test
    public void checkReturnMaxDiscountTenthTicketStrategy() {
        // when
        Integer discount = discountService.getDiscount(user3, event1, event1.getStartTime());
        // then
        assertThat(DISCOUNT_MESSAGE, discount, is(50));
    }

    @Test
    public void checkReturnDiscountZero() {
        // when
        Integer discount = discountService.getDiscount(user2, event1, event1.getStartTime());
        // then
        assertThat(DISCOUNT_MESSAGE, discount, is(0));
    }
}