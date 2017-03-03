package com.epam.university.service.unittest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.university.dao.TicketDao;
import com.epam.university.dao.UserDao;
import com.epam.university.entity.Ticket;
import com.epam.university.entity.User;
import com.epam.university.service.UserService;
import com.epam.university.service.impl.UserServiceImpl;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String USER_MESSAGE = "Users are not equals";
    private static final String USER_NULL_MESSAGE = "User is null";
    private static final String LIST_BOOKED_TICKETS_MESSAGE = "Lists are not equals";
    private static final String LIST_BOOKED_TICKETS_NULL_MESSAGE = "List is null";

    private static final String USER_ID_FIELD_1 = "userId1";
    private static final String USER_EMAIL_FIELD_1 = "userEmail1";
    private static final String USER_NAME_FIELD_1 = "userName1";

    @Mock
    private UserDao userDao;

    @Mock
    private TicketDao ticketDao;

    @InjectMocks
    private final UserService userService = new UserServiceImpl();

    @Test
    public void shouldRegisterUser() {
        // given
        User user = new User();
        // when
        userService.register(user);
        // then
        verify(userDao).create(user);
    }

    @Test
    public void shouldRemoveUser() {
        // given
        User user = new User();
        user.setUserId(USER_ID_FIELD_1);
        // when
        userService.remove(user);
        // then
        verify(userDao).remove(USER_ID_FIELD_1);
    }

    @Test
    public void shouldReturnUserById() {
        // given
        User user = new User();
        user.setUserId(USER_ID_FIELD_1);
        given(userDao.getUserById(USER_ID_FIELD_1)).willReturn(user);
        // when
        User resultUser = userService.getUserById(USER_ID_FIELD_1);
        // then
        verify(userDao).getUserById(USER_ID_FIELD_1);
        assertThat(USER_MESSAGE, resultUser, is(equalTo(user)));
    }

    @Test
    public void shouldNotReturnUserById() {
        // given
        given(userDao.getUserById(anyString())).willReturn(null);
        // when
        User resultUser = userService.getUserById(USER_ID_FIELD_1);
        // then
        verify(userDao).getUserById(USER_ID_FIELD_1);
        assertThat(USER_NULL_MESSAGE, resultUser, nullValue());
    }

    @Test
    public void shouldReturnUserByEmail() {
        // given
        User user = new User();
        user.setEmail(USER_EMAIL_FIELD_1);
        given(userDao.getUserByEmail(USER_EMAIL_FIELD_1)).willReturn(user);
        // when
        User resultUser = userService.getUserByEmail(USER_EMAIL_FIELD_1);
        // then
        verify(userDao).getUserByEmail(USER_EMAIL_FIELD_1);
        assertThat(USER_MESSAGE, resultUser, is(equalTo(user)));
    }

    @Test
    public void shouldNotReturnUserByEmail() {
        // given
        given(userDao.getUserByEmail(anyString())).willReturn(null);
        // when
        User resultUser = userService.getUserByEmail(USER_EMAIL_FIELD_1);
        // then
        verify(userDao).getUserByEmail(USER_EMAIL_FIELD_1);
        assertThat(USER_NULL_MESSAGE, resultUser, nullValue());
    }

    @Test
    public void shouldReturnUserByName() {
        // given
        User user = new User();
        user.setName(USER_NAME_FIELD_1);
        given(userDao.getUserByName(USER_NAME_FIELD_1)).willReturn(user);
        // when
        User resultUser = userService.getUserByName(USER_NAME_FIELD_1);
        // then
        verify(userDao).getUserByName(USER_NAME_FIELD_1);
        assertThat(USER_MESSAGE, resultUser, is(equalTo(user)));
    }

    @Test
    public void shouldNotReturnUserByName() {
        // given
        given(userDao.getUserByName(anyString())).willReturn(null);
        // when
        User resultUser = userService.getUserByName(USER_NAME_FIELD_1);
        // then
        verify(userDao).getUserByName(USER_NAME_FIELD_1);
        assertThat(USER_NULL_MESSAGE, resultUser, nullValue());
    }

    @Test
    public void shouldReturnListOfBookedTickets() {
        // given
        User user = new User();
        user.setUserId(USER_ID_FIELD_1);
        Ticket ticket = new Ticket();
        given(ticketDao.getTicketsByUserId(USER_ID_FIELD_1)).willReturn(Lists.newArrayList(ticket));
        // when
        List<Ticket> resultListBookedTickets = userService.getBookedTickets(user);
        // then
        verify(ticketDao).getTicketsByUserId(USER_ID_FIELD_1);
        assertThat(LIST_BOOKED_TICKETS_MESSAGE, resultListBookedTickets.size(), is(1));
        assertThat(LIST_BOOKED_TICKETS_MESSAGE, resultListBookedTickets, hasItem(ticket));
    }

    @Test
    public void shouldNotReturnListOfBookedTickets() {
        // given
        User user = new User();
        user.setUserId(USER_ID_FIELD_1);
        given(ticketDao.getTicketsByUserId(anyString())).willReturn(null);
        // when
        List<Ticket> resultListBookedTickets = userService.getBookedTickets(user);
        // then
        verify(ticketDao).getTicketsByUserId(USER_ID_FIELD_1);
        assertThat(LIST_BOOKED_TICKETS_NULL_MESSAGE, resultListBookedTickets, nullValue());
    }
}
