package org.cachewrapper.data.repository;

import org.cachewrapper.data.domain.UserCredentialsEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserCredentialsEntityRepository extends JpaRepository<UserCredentialsEntity, UUID> {

    Optional<UserCredentialsEntity> findByEmail(@NotNull String email);

    Optional<UserCredentialsEntity> findByUsername(@NotNull String username);
}