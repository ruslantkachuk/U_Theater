package com.epam.university.service.unittest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.epam.university.entity.Event;
import com.epam.university.entity.User;
import com.epam.university.service.DiscountService;
import com.epam.university.service.impl.DiscountServiceImpl;
import com.epam.university.strategy.DiscountStrategy;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class DiscountServiceImplTest {

    private static final String DISCOUNT_MESSAGE = "Discounts are not equals";

    private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("price");

    @Spy
    private final List<DiscountStrategy> discountStrategies = Lists.newArrayList();
    @Mock
    private DiscountStrategy discountStrategy;
    @InjectMocks
    private final DiscountService discountService = new DiscountServiceImpl();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(discountService, "discountNo", Integer.valueOf(RESOURCE.getString("discount.no")));
        MockitoAnnotations.initMocks(this);
        discountStrategies.add(discountStrategy);
    }

    @Test
    public void shouldReturnDiscount() {
        // given
        Integer discount = Integer.valueOf(5);
        given(discountStrategy.getPercentDiscount(any(User.class), any(Date.class))).willReturn(discount);
        // when
        Integer resultDiscount = discountService.getDiscount(new User(), new Event(), new Date());
        // then
        assertThat(DISCOUNT_MESSAGE, resultDiscount, equalTo(discount));
    }
}
