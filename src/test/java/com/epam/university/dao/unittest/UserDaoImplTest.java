package com.epam.university.dao.unittest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.epam.university.dao.UserDao;
import com.epam.university.dao.impl.UserDaoImpl;
import com.epam.university.entity.User;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest {

    private static final String USER_MESSAGE = "Users are not equals";

    private static final String USER_ID_FIELD = "userId";
    private static final String USER_NAME_FIELD = "userName";
    private static final String USER_SURNAME_FIELD = "userSurname";
    private static final String USER_EMAIL_FIELD = "userEmail";
    private static final Date USER_BIRTHDAY_FIELD = new Date();

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private final UserDao userDao = new UserDaoImpl();

    @Test
    public void shouldCreateUser() {
        // given
        User user = new User();
        user.setUserId(USER_ID_FIELD);
        user.setName(USER_NAME_FIELD);
        user.setSurname(USER_SURNAME_FIELD);
        user.setEmail(USER_EMAIL_FIELD);
        user.setBirthday(USER_BIRTHDAY_FIELD);
        // when
        userDao.create(user);
        // then
        verify(jdbcTemplate).update(anyString(), eq(USER_ID_FIELD), eq(USER_NAME_FIELD), eq(USER_SURNAME_FIELD),
                eq(USER_BIRTHDAY_FIELD), eq(USER_EMAIL_FIELD));
    }

    @Test
    public void shouldRemoveUser() {
        // given
        User user = new User();
        user.setUserId(USER_ID_FIELD);
        // when
        userDao.remove(USER_ID_FIELD);
        // then
        verify(jdbcTemplate).update(anyString(), eq(USER_ID_FIELD));
    }

    @Test
    public void shouldReturnUserById() {
        // given
        User user = new User();
        user.setUserId(USER_ID_FIELD);
        given(jdbcTemplate.queryForObject(anyString(), eq(new Object[] { USER_ID_FIELD }), any(RowMapper.class)))
                .willReturn(user);
        // when
        User resultUser = userDao.getUserById(USER_ID_FIELD);
        // then
        verify(jdbcTemplate).queryForObject(anyString(), eq(new Object[] { USER_ID_FIELD }), any(RowMapper.class));
        assertThat(USER_MESSAGE, resultUser, is(equalTo(user)));
    }

    @Test
    public void shouldReturnUserByEmail() {
        // given
        User user = new User();
        user.setEmail(USER_EMAIL_FIELD);
        given(jdbcTemplate.queryForObject(anyString(), eq(new Object[] { USER_EMAIL_FIELD }), any(RowMapper.class)))
                .willReturn(user);
        // when
        User resultUser = userDao.getUserByEmail(USER_EMAIL_FIELD);
        // then
        verify(jdbcTemplate).queryForObject(anyString(), eq(new Object[] { USER_EMAIL_FIELD }), any(RowMapper.class));
        assertThat(USER_MESSAGE, resultUser, is(equalTo(user)));
    }

    @Test
    public void shouldReturnUserByName() {
        // given
        User user = new User();
        user.setName(USER_NAME_FIELD);
        given(jdbcTemplate.queryForObject(anyString(), eq(new Object[] { USER_NAME_FIELD }), any(RowMapper.class)))
                .willReturn(user);
        // when
        User resultUser = userDao.getUserByName(USER_NAME_FIELD);
        // then
        verify(jdbcTemplate).queryForObject(anyString(), eq(new Object[] { USER_NAME_FIELD }), any(RowMapper.class));
        assertThat(USER_MESSAGE, resultUser, is(equalTo(user)));
    }

}
