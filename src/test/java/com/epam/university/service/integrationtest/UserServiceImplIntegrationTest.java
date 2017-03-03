package com.epam.university.service.integrationtest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.university.entity.Ticket;
import com.epam.university.entity.User;
import com.epam.university.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring.xml", "/spring-test.xml" })
public class UserServiceImplIntegrationTest {

    private static final String USER_MESSAGE = "Users are not equals";

    private static final String USER1_ID_FIELD = "user1Id";
    private static final String USER1_NAME_FIELD = "user1Name";
    private static final String USER1_EMAIL_FIELD = "user1@email.com";

    private static final String USER_ID_FIELD = "userId";
    private static final String USER_NAME_FIELD = "userName";
    private static final String USER_SURNAME_FIELD = "userSurname";
    private static final String USER_EMAIL_FIELD = "user@email.com";
    private static final Date USER_BIRTHDAY_FIELD = new Date();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Resource
    private UserService userService;

    @Test
    public void checkRegisterUser() {
        // given
        User user = new User();
        user.setUserId(USER_ID_FIELD);
        user.setName(USER_NAME_FIELD);
        user.setSurname(USER_SURNAME_FIELD);
        user.setEmail(USER_EMAIL_FIELD);
        user.setBirthday(USER_BIRTHDAY_FIELD);
        // when
        userService.register(user);
        // then
        System.out.println(user);
        System.out.println("++++++");
        System.out.println(userService.getUserById(USER_ID_FIELD));
        assertThat(USER_MESSAGE, userService.getUserById(USER_ID_FIELD), equalTo(user));
    }

    @Test
    public void checkRemoveUser() {
        // given
        User user = new User();
        user.setUserId(USER_ID_FIELD);
        user.setName(USER_NAME_FIELD);
        user.setSurname(USER_SURNAME_FIELD);
        user.setEmail(USER_EMAIL_FIELD);
        user.setBirthday(USER_BIRTHDAY_FIELD);
        userService.register(user);
        // when
        userService.remove(user);
        // then
        exception.expect(EmptyResultDataAccessException.class);
        userService.getUserById(USER_ID_FIELD);
    }

    @Test
    public void checkReturnUserById() {
        // when
        User resultUser = userService.getUserById(USER1_ID_FIELD);
        // then
        assertThat(USER_MESSAGE, resultUser, notNullValue());
    }

    @Test
    public void checkReturnUserByName() {
        // when
        User resultUser = userService.getUserByName(USER1_NAME_FIELD);
        // then
        assertThat(USER_MESSAGE, resultUser, notNullValue());
    }

    @Test
    public void checkReturnUserByEmail() {
        // when
        User resultUser = userService.getUserByEmail(USER1_EMAIL_FIELD);
        // then
        assertThat(USER_MESSAGE, resultUser, notNullValue());
    }

    // TODO
    @Test
    public void checkReturnListBookedTickets() {
        // given
        User user = new User();
        user.setUserId(USER1_ID_FIELD);
        // when
        List<Ticket> resultListBookedTickets = userService.getBookedTickets(user);
        // then
        assertThat(USER_MESSAGE, resultListBookedTickets, notNullValue());
    }
}
