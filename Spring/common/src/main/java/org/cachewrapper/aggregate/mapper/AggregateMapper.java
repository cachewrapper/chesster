package org.cachewrapper.aggregate.mapper;

import org.cachewrapper.aggregate.Aggregate;

public interface AggregateMapper<T, A extends Aggregate> {
    A toAggregate(T entity);
    T toEntity(A aggregate);
}