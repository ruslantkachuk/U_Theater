package com.epam.university.strategy;

import java.util.Date;

import com.epam.university.entity.User;

public interface DiscountStrategy {
    int getPercentDiscount(User user, Date date);
}
