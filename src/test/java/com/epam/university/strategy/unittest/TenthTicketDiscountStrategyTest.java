package com.epam.university.strategy.unittest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.epam.university.dao.TicketDao;
import com.epam.university.entity.User;
import com.epam.university.strategy.DiscountStrategy;
import com.epam.university.strategy.impl.TenthTicketDiscountStrategy;

@RunWith(MockitoJUnitRunner.class)
public class TenthTicketDiscountStrategyTest {
    private static final String PERCENT_DISCOUNT_MESSAGE = "Percent discounts are not equals";
    private static final ResourceBundle RESOURCE = ResourceBundle.getBundle("price");

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private TicketDao ticketDao;

    @InjectMocks
    private final DiscountStrategy discountStrategy = new TenthTicketDiscountStrategy();

    @Before
    public void setUp() {
        ReflectionTestUtils
                .setField(discountStrategy, "discountNo", Integer.valueOf(RESOURCE.getString("discount.no")));
        ReflectionTestUtils.setField(discountStrategy, "discountTenthTicket",
                Integer.valueOf(RESOURCE.getString("discount.tenthTicket")));
        ReflectionTestUtils.setField(discountStrategy, "ticketRemainder",
                Integer.valueOf(RESOURCE.getString("ticket.remainder")));
        ReflectionTestUtils.setField(discountStrategy, "ticketDivider",
                Integer.valueOf(RESOURCE.getString("ticket.divider")));
    }

    @Test
    public void shouldReturnPercentDiscount() {
        // given
        given(ticketDao.getTicketsByUserId(anyString()).size()).willReturn(9);
        // when
        int percentDiscount = discountStrategy.getPercentDiscount(new User(), null);
        // then
        assertThat(PERCENT_DISCOUNT_MESSAGE, percentDiscount, is(50));
    }

    @Test
    public void shouldNotReturnPercentDiscount() {
        // given
        given(ticketDao.getTicketsByUserId(anyString()).size()).willReturn(8);
        // when
        int percentDiscount = discountStrategy.getPercentDiscount(new User(), null);
        // then
        assertThat(PERCENT_DISCOUNT_MESSAGE, percentDiscount, is(0));
    }
}
