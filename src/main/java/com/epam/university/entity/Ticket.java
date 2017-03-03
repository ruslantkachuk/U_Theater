package com.epam.university.entity;

import com.google.common.base.Objects;

public class Ticket {
    private String ticketId;
    private User user;
    private Event event;
    private Integer seatNumber;
    private Double price;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(final String ticketId) {
        this.ticketId = ticketId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(final Event event) {
        this.event = event;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(final Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equal(getTicketId(), ticket.getTicketId()) &&
                Objects.equal(getUser(), ticket.getUser()) &&
                Objects.equal(getEvent(), ticket.getEvent()) &&
                Objects.equal(getSeatNumber(), ticket.getSeatNumber()) &&
                Objects.equal(getPrice(), ticket.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTicketId(), getUser(), getEvent(), getSeatNumber(), getPrice());
    }
}
