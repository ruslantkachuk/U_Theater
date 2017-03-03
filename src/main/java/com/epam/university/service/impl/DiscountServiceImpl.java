package com.epam.university.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.epam.university.entity.Event;
import com.epam.university.entity.User;
import com.epam.university.service.DiscountService;
import com.epam.university.strategy.DiscountStrategy;

public class DiscountServiceImpl implements DiscountService {

    private List<DiscountStrategy> discountStrategies;

    @Value("${discount.no}")
    private Integer discountNo;

    public int getDiscount(final User user, final Event event, final Date date) {
        int maxPercentDiscount = discountNo;
        for (DiscountStrategy discountStrategy : discountStrategies) {
            maxPercentDiscount = Math.max(discountStrategy.getPercentDiscount(user, date), maxPercentDiscount);
        }
        return maxPercentDiscount;
    }

    public void setDiscountStrategies(final List<DiscountStrategy> discountStrategies) {
        this.discountStrategies = discountStrategies;
    }
}
