package com.epam.university.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.epam.university.dao.TicketDao;
import com.epam.university.dao.UserDao;
import com.epam.university.entity.Ticket;
import com.epam.university.entity.User;
import com.epam.university.service.UserService;

public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private TicketDao ticketDao;

    public void register(final User user) {
        userDao.create(user);
    }

    public void remove(final User user) {
        userDao.remove(user.getUserId());
    }

    public User getUserById(final String userId) {
        return userDao.getUserById(userId);
    }

    public User getUserByEmail(final String userEmail) {
        return userDao.getUserByEmail(userEmail);
    }

    public User getUserByName(final String userName) {
        return userDao.getUserByName(userName);
    }

    public List<Ticket> getBookedTickets(final User user) {
        return ticketDao.getTicketsByUserId(user.getUserId());
    }
}
