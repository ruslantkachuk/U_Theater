package com.epam.university.aspect;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.EmptyResultDataAccessException;

import com.epam.university.dao.CounterDao;
import com.epam.university.entity.Counter;
import com.epam.university.entity.User;

@Aspect
public class DiscountAspect {

    public static final String DISCOUNT_WAS_GIVEN_TOTAL = "Discount";

    @Resource
    private CounterDao counterDao;

    /**
     * Join point. How many times each discount was given total
     */
    @Pointcut("execution(int com.epam.university.service.DiscountService.getDiscount(..))")
    public void discountWasGivenTotal() {
    }

    /**
     * Join point. How many times each discount was given total for specific user
     */
    @Pointcut("execution(int com.epam.university.service.DiscountService.getDiscount(com.epam.university.entity.User, ..)) && args(user,..)")
    public void discountWasGivenTotalForSpecificUser(final User user) {
    }

    @AfterReturning(pointcut = "discountWasGivenTotal()", returning = "discount")
    public void countDiscountWasGivenTotal(final int discount) {
        if (discount != 0) {
            countDiscountWasGivenTotalCommon(DISCOUNT_WAS_GIVEN_TOTAL, "", int.class.getSimpleName());
        }
    }

    @Around("discountWasGivenTotalForSpecificUser(user)")
    public int countDiscountWasGivenTotalForSpecificUser(final ProceedingJoinPoint pjp, final User user)
            throws Throwable {
        Integer result = (Integer) pjp.proceed();
        if (result != 0) {
            countDiscountWasGivenTotalCommon(DISCOUNT_WAS_GIVEN_TOTAL + user.getUserId(), user.getUserId(), user
                    .getClass().getSimpleName());
        }
        return result;
    }

    private void countDiscountWasGivenTotalCommon(final String counterId, final String entityId, final String entityType) {
        try {
            Counter counter = counterDao.getCounterByIdAndEntityIdAndEntityType(counterId, entityId, entityType);
            counter.setEntityCount(counter.getEntityCount() + 1);
            counterDao.update(counter);
        } catch (EmptyResultDataAccessException exception) {
            Counter newCounter = new Counter();
            newCounter.setCounterId(counterId);
            newCounter.setEntityId(entityId);
            newCounter.setEntityType(entityType);
            newCounter.setEntityCount(1);
            counterDao.create(newCounter);
        }
    }
}
