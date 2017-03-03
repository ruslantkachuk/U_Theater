package com.epam.university.entity;

import com.google.common.base.Objects;

import java.util.List;

public class Auditorium {
    private String auditoriumId;
    private String name;
    private Integer seats;
    private List<Integer> vipSeats;

    public String getAuditoriumId() {
        return auditoriumId;
    }

    public void setAuditoriumId(final String auditoriumId) {
        this.auditoriumId = auditoriumId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(final Integer seats) {
        this.seats = seats;
    }

    public List<Integer> getVipSeats() {
        return vipSeats;
    }

    public void setVipSeats(final List<Integer> vipSeats) {
        this.vipSeats = vipSeats;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auditorium that = (Auditorium) o;
        return Objects.equal(getAuditoriumId(), that.getAuditoriumId()) &&
                Objects.equal(getName(), that.getName()) &&
                Objects.equal(getSeats(), that.getSeats()) &&
                Objects.equal(getVipSeats(), that.getVipSeats());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAuditoriumId(), getName(), getSeats(), getVipSeats());
    }
}
