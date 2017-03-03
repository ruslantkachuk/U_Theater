package com.epam.university.aspect;

import javax.annotation.Resource;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.EmptyResultDataAccessException;

import com.epam.university.dao.CounterDao;
import com.epam.university.entity.Counter;
import com.epam.university.entity.Event;
import com.epam.university.entity.Ticket;

@Aspect
public class CounterAspect {

    public static final String COUNTER_EVENT_BY_NAME_ID = "getEventByName_";
    public static final String COUNTER_EVENT_TICKET_PRICE_ID = "getTicketPrice_";
    public static final String COUNTER_EVENT_BOOK_TICKET_ID = "bookTicket_";

    @Resource
    private CounterDao counterDao;

    /**
     * Join point. How many times each event was accessed by name
     */
    @Pointcut("execution(com.epam.university.entity.Event com.epam.university.service.EventService.getEventByName(..))")
    public void eventAccessedByName() {
    }

    /**
     * Join point. How many times each events prices were queried
     */
    @Pointcut("execution(java.lang.Double com.epam.university.service.BookingService.getTicketPrice(com.epam.university.entity.Event,..)) && args(event,..)")
    public void eventPriceQueried(final Event event) {
    }

    /**
     * Join point. How many times each events tickets were booked
     */
    @Pointcut("execution(* com.epam.university.service.BookingService.bookTicket(*,com.epam.university.entity.Ticket)) && args(*,ticket)")
    public void eventTicketBooked(final Ticket ticket) {
    }

    @AfterReturning(pointcut = "eventAccessedByName()", returning = "event")
    public void countEventAccessedByName(final Event event) {
        countEvent(event, COUNTER_EVENT_BY_NAME_ID + event.getEventId());
    }

    @Before("eventPriceQueried(event)")
    public void countEventPriceQueried(final Event event) {
        countEvent(event, COUNTER_EVENT_TICKET_PRICE_ID + event.getEventId());
    }

    @Before("eventTicketBooked(ticket)")
    public void countEventTicketBooked(final Ticket ticket) {
        countEvent(ticket.getEvent(), COUNTER_EVENT_BOOK_TICKET_ID + ticket.getEvent().getEventId());
    }

    private void countEvent(final Event event, final String counterId) {
        if (null != event) {
            try {
                Counter counter = counterDao.getCounterByIdAndEntityIdAndEntityType(counterId, event.getEventId(),
                        event.getClass().getSimpleName());
                counter.setEntityCount(counter.getEntityCount() + 1);
                counterDao.update(counter);
            } catch (EmptyResultDataAccessException exception) {
                Counter newCounter = new Counter();
                newCounter.setCounterId(counterId);
                newCounter.setEntityId(event.getEventId());
                newCounter.setEntityType(event.getClass().getSimpleName());
                newCounter.setEntityCount(1);
                counterDao.create(newCounter);
            }
        }
    }
}
