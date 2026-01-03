package org.cachewrapper.aggregate.repository;

import org.cachewrapper.aggregate.UserCredentialsAggregate;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsAggregateRepository extends AggregateRepository<UserCredentialsAggregate> {

    Optional<UserCredentialsAggregate> findUserCredentialsAggregateByEmail(@NotNull String email);

    Optional<UserCredentialsAggregate> findUserCredentialsAggregateByUsername(@NotNull String username);
}
