package com.epam.university.entity;

import com.google.common.base.Objects;

import java.util.Date;

public class Event {
    private String eventId;
    private String name;
    private Double price;
    private Rating rating;
    private Date startTime;
    private Date endTime;
    private Auditorium auditorium;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(final String eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(final Rating rating) {
        this.rating = rating;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(final Date endTime) {
        this.endTime = endTime;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(final Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equal(getEventId(), event.getEventId()) &&
                Objects.equal(getName(), event.getName()) &&
                Objects.equal(getPrice(), event.getPrice()) &&
                Objects.equal(getRating(), event.getRating()) &&
                Objects.equal(getStartTime(), event.getStartTime()) &&
                Objects.equal(getEndTime(), event.getEndTime()) &&
                Objects.equal(getAuditorium(), event.getAuditorium());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEventId(), getName(), getPrice(), getRating(), getStartTime(), getEndTime(), getAuditorium());
    }
}
