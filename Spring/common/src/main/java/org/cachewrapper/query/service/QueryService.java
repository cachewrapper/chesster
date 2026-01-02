package org.cachewrapper.query.service;

import org.cachewrapper.aggregate.Aggregate;
import org.cachewrapper.query.domain.View;

public interface QueryService<T extends View, K> {
    T findById(K id);
}