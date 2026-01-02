package org.cachewrapper.aggregate.repository;

import org.cachewrapper.aggregate.Aggregate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AggregateRepository<T extends Aggregate> extends JpaRepository<T, UUID> {}