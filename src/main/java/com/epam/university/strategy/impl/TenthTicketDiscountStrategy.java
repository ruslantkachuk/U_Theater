package com.epam.university.strategy.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;

import com.epam.university.dao.TicketDao;
import com.epam.university.entity.User;
import com.epam.university.strategy.DiscountStrategy;

public class TenthTicketDiscountStrategy implements DiscountStrategy {

    @Resource
    private TicketDao ticketDao;

    @Value("${discount.no}")
    private Integer discountNo;

    @Value("${discount.tenthTicket}")
    private Integer discountTenthTicket;

    @Value("${ticket.remainder}")
    private Integer ticketRemainder;

    @Value("${ticket.divider}")
    private Integer ticketDivider;

    public int getPercentDiscount(final User user, final Date date) {
        return ticketDao.getTicketsByUserId(user.getUserId()).size() % ticketDivider == ticketRemainder ? discountTenthTicket
                : discountNo;
    }
}
