package com.epam.university.aspect;

import java.util.Random;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.epam.university.dao.CounterDao;
import com.epam.university.entity.Counter;
import com.epam.university.entity.Ticket;
import com.epam.university.entity.User;

@Aspect
public class LuckyWinnerAspect {

    public static final String LUCKY_WINNER_TICKET = "LuckyWinnerTicket_";

    @Resource
    private CounterDao counterDao;

    /**
     * Join point. Every time the bookTicket method is executed perform the checkLucky method for the user
     * that based on some randomness will return true or false. If user is lucky, the ticketPrice changes
     * to zero and ticket is booked, thus user pays nothing
     */
    @Pointcut("execution(* com.epam.university.service.BookingService.bookTicket(com.epam.university.entity.User,com.epam.university.entity.Ticket)) && args(user,ticket)")
    public void checkLuckyUser(final User user, final Ticket ticket) {
    }

    @Around("checkLuckyUser(user, ticket)")
    public Object checkLucky(final ProceedingJoinPoint pjp, final User user, final Ticket ticket) throws Throwable {
        if ((new Random()).nextBoolean()) {
            ticket.setPrice(0d);

            Counter newCounter = new Counter();
            newCounter.setCounterId(LUCKY_WINNER_TICKET + ticket.getTicketId());
            newCounter.setEntityId(ticket.getTicketId());
            newCounter.setEntityType(ticket.getClass().getSimpleName());
            newCounter.setEntityCount(1);
            counterDao.create(newCounter);
        }
        return pjp.proceed(new Object[] { user, ticket });
    }
}
