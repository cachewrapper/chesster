package org.cachewrapper.aggregate.mapper;

import org.cachewrapper.aggregate.Aggregate;
import org.cachewrapper.query.domain.View;

public interface AggregateMapper<V extends View, A extends Aggregate> {
    A mapAggregate(V view);
    V mapView(A aggregate);
}