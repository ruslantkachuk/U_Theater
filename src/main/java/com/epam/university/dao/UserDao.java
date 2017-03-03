package com.epam.university.dao;

import com.epam.university.entity.User;

public interface UserDao {
    void create(User user);

    void remove(String userId);

    User getUserById(String userId);

    User getUserByEmail(String userEmail);

    User getUserByName(String userName);

}
