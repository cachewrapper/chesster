package org.cachewrapper.query.repository;

import jakarta.transaction.Transactional;
import org.cachewrapper.query.domain.UserCredentialsView;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserCredentialsViewRepository extends ViewRepository<UserCredentialsView, UUID> {

    Optional<UserCredentialsView> findUserCredentialsViewByEmail(@NotNull String email);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM user_credentials_view_refresh_tokens
            WHERE user_uuid = :userUUID
            AND refresh_token = :refreshTokenString
            """, nativeQuery = true)
    void deleteRefreshToken(
            @Param("userUUID") @NotNull UUID userUUID,
            @Param("refreshTokenString") @NotNull String refreshTokenString
    );

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO user_credentials_view_refresh_tokens(user_uuid, refresh_token)
            VALUES (:userUUID, :refreshTokenString)
            ON CONFLICT (user_uuid, refresh_token_hash) DO NOTHING
            """, nativeQuery = true)
    void insertRefreshToken(
            @Param("userUUID") @NotNull UUID userUUID,
            @Param("refreshTokenString") @NotNull String refreshTokenString
    );

}