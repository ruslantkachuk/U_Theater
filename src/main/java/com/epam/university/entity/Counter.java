package com.epam.university.entity;

import com.google.common.base.Objects;

public class Counter {
    private String counterId;
    private String entityId;
    private String entityType;
    private Integer entityCount;

    public String getCounterId() {
        return counterId;
    }

    public void setCounterId(final String counterId) {
        this.counterId = counterId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(final String entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(final String entityType) {
        this.entityType = entityType;
    }

    public Integer getEntityCount() {
        return entityCount;
    }

    public void setEntityCount(final Integer entityCount) {
        this.entityCount = entityCount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Counter counter = (Counter) o;
        return Objects.equal(getCounterId(), counter.getCounterId()) &&
                Objects.equal(getEntityId(), counter.getEntityId()) &&
                Objects.equal(getEntityType(), counter.getEntityType()) &&
                Objects.equal(getEntityCount(), counter.getEntityCount());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCounterId(), getEntityId(), getEntityType(), getEntityCount());
    }
}
