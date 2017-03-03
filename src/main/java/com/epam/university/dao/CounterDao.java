package com.epam.university.dao;

import com.epam.university.entity.Counter;

public interface CounterDao {
    void create(Counter counter);

    void update(Counter counter);

    Counter getCounterByIdAndEntityIdAndEntityType(String counterId, String entityId, String entityType);
}