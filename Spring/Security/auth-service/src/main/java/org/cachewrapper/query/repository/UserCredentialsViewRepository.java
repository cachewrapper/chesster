package org.cachewrapper.query.repository;

import org.cachewrapper.query.domain.UserCredentialsView;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserCredentialsViewRepository extends ViewRepository<UserCredentialsView, UUID> {

    Optional<UserCredentialsView> findUserCredentialsViewByEmail(@NotNull String email);
}