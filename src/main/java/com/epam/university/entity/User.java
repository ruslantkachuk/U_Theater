package com.epam.university.entity;

import com.google.common.base.Objects;

import java.util.Date;

public class User {
    private String userId;
    private String name;
    private String surname;
    private Date birthday;
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(getUserId(), user.getUserId()) &&
                Objects.equal(getName(), user.getName()) &&
                Objects.equal(getSurname(), user.getSurname()) &&
                Objects.equal(getBirthday(), user.getBirthday()) &&
                Objects.equal(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUserId(), getName(), getSurname(), getBirthday(), getEmail());
    }
}
