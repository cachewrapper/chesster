package org.cachewrapper.token.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import jakarta.transaction.Transactional;
import org.cachewrapper.token.repository.domain.RefreshTokenEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

    @Transactional
    void deleteByUserUUIDAndRefreshTokenString(@NotNull UUID userUUID, @NotNull String refreshTokenString);

    @Transactional
    @Modifying
    @Query("""
                UPDATE RefreshTokenEntity refreshTokenEntity
                SET refreshTokenEntity.refreshTokenUUID = :newUUID, refreshTokenEntity.refreshTokenString = :newToken
                WHERE refreshTokenEntity.userUUID = :userUUID AND refreshTokenEntity.refreshTokenString = :oldToken
            """)
    void replaceRefreshToken(@Param("userUUID") UUID userUUID,
                            @Param("newUUID") UUID newUUID,
                            @Param("oldToken") String oldToken,
                            @Param("newToken") String newToken
    );
}