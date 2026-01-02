package org.cachewrapper.aggregate.repository;

import org.cachewrapper.aggregate.UserAggregate;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAggregateRepository extends AggregateRepository<UserAggregate> {

    Optional<UserAggregate> findUserAggregateByEmail(@NotNull String email);

    Optional<UserAggregate> findUserAggregateByUsername(@NotNull String username);
}