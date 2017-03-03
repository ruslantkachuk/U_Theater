package com.epam.university.service;

import java.util.Date;

import com.epam.university.entity.Event;
import com.epam.university.entity.User;

public interface DiscountService {
    int getDiscount(User user, Event event, Date date);

}
