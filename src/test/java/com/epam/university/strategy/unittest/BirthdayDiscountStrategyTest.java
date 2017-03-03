package com.epam.university.strategy.unittest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ResourceBundle;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.epam.university.entity.User;
import com.epam.university.strategy.DiscountStrategy;
import com.epam.university.strategy.impl.BirthdayDiscountStrategy;

@RunWith(MockitoJUnitRunner.class)
public class BirthdayDiscountStrategyTest {

    private static final String PERCENT_DISCOUNT_MESSAGE = "Percent discounts are not equals";
    private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("price");
    private final DiscountStrategy discountStrategy = new BirthdayDiscountStrategy();

    @Before
    public void setUp() {
        ReflectionTestUtils
                .setField(discountStrategy, "discountNo", Integer.valueOf(RESOURCE.getString("discount.no")));
        ReflectionTestUtils.setField(discountStrategy, "discountBirthday",
                Integer.valueOf(RESOURCE.getString("discount.birthday")));
    }

    @Test
    public void shouldReturnPercentDiscount() {
        // given
        DateTime birthday = new DateTime(2016, 1, 12, 0, 0);
        User user = new User();
        user.setBirthday(birthday.toDate());
        // when
        int percentDiscount = discountStrategy.getPercentDiscount(user, birthday.toDate());
        // then
        assertThat(PERCENT_DISCOUNT_MESSAGE, percentDiscount, is(5));
    }

    @Test
    public void shouldNotReturnPercentDiscount() {
        // given
        DateTime birthday = new DateTime(2016, 1, 12, 0, 0);
        User user = new User();
        user.setBirthday(birthday.plusDays(1).toDate());
        // when
        int percentDiscount = discountStrategy.getPercentDiscount(user, birthday.toDate());
        // then
        assertThat(PERCENT_DISCOUNT_MESSAGE, percentDiscount, is(0));
    }

}