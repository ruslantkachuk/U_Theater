package com.epam.university.service;

import java.util.List;

import com.epam.university.entity.Ticket;
import com.epam.university.entity.User;

public interface UserService {
    void register(User user);

    void remove(User user);

    User getUserById(String userId);

    User getUserByEmail(String userEmail);

    User getUserByName(String userName);

    List<Ticket> getBookedTickets(User user);
}
