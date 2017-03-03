package com.epam.university.strategy.impl;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;

import com.epam.university.entity.User;
import com.epam.university.strategy.DiscountStrategy;

public class BirthdayDiscountStrategy implements DiscountStrategy {

    @Value("${discount.no}")
    private Integer discountNo;

    @Value("${discount.birthday}")
    private Integer discountBirthday;

    public int getPercentDiscount(final User user, final Date date) {

        Integer birthdayDayOfMonth = new DateTime(user.getBirthday()).getDayOfMonth();
        Integer birthdayMonthOfYear = new DateTime(user.getBirthday()).getDayOfYear();

        Integer eventDayOfMonth = new DateTime(date).getDayOfMonth();
        Integer eventMonthOfYear = new DateTime(date).getDayOfYear();

        return (birthdayDayOfMonth.equals(eventDayOfMonth) && birthdayMonthOfYear.equals(eventMonthOfYear)) ? discountBirthday
                : discountNo;
    }
}
