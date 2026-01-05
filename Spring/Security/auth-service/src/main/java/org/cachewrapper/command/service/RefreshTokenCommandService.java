package org.cachewrapper.command.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.domain.RefreshTokenCommand;
import org.cachewrapper.command.response.JsonWebTokenResponse;
import org.cachewrapper.exception.RefreshTokenInvalidException;
import org.cachewrapper.exception.RefreshTokenNotFoundException;
import org.cachewrapper.token.domain.payload.AccessTokenPayload;
import org.cachewrapper.token.domain.payload.RefreshTokenPayload;
import org.cachewrapper.token.domain.token.RefreshToken;
import org.cachewrapper.token.repository.RefreshTokenRepository;
import org.cachewrapper.token.repository.domain.RefreshTokenEntity;
import org.cachewrapper.token.service.token.AccessTokenService;
import org.cachewrapper.token.service.token.RefreshTokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class RefreshTokenCommandService implements CommandService<JsonWebTokenResponse, RefreshTokenCommand> {

    private final ConcurrentHashMap<UUID, ReentrantLock> locks = new ConcurrentHashMap<>();

    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenService accessTokenService;

    @Override
    @Transactional
    public JsonWebTokenResponse execute(RefreshTokenCommand command) {
        final UUID refreshTokenUUID = command.refreshTokenUUID();
        final RefreshTokenEntity refreshTokenEntity = refreshTokenRepository
                .findById(refreshTokenUUID)
                .orElseThrow(RefreshTokenNotFoundException::new);

        final String refreshTokenString = refreshTokenEntity.getRefreshTokenString();
        if (!refreshTokenService.validateTokenString(refreshTokenString)) {
            throw new RefreshTokenInvalidException();
        }

        final RefreshToken refreshToken = refreshTokenService.getTokenFromString(refreshTokenString);
        final UUID userUUID = refreshToken.userUUID();
        final String username = refreshToken.username();

        final ReentrantLock lock = locks.computeIfAbsent(userUUID, _ -> new ReentrantLock());
        lock.lock();

        try {
            return createResponse(userUUID, username, refreshTokenString);
        } finally {
            lock.unlock();
        }
    }

    @NotNull
    private JsonWebTokenResponse createResponse(
            @NotNull UUID userUUID,
            @NotNull String username,
            @NotNull String oldRefreshTokenString
    ) {
        final AccessTokenPayload accessTokenPayload = new AccessTokenPayload(userUUID, username);
        final String accessTokenString = accessTokenService.generateTokenString(accessTokenPayload);

        final RefreshTokenPayload refreshTokenPayload = new RefreshTokenPayload(userUUID, username);
        final String refreshTokenString = refreshTokenService.generateTokenString(refreshTokenPayload);

        final RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(UUID.randomUUID(), userUUID, refreshTokenString);
        final UUID refreshTokenUUID = refreshTokenEntity.getRefreshTokenUUID();

        refreshTokenRepository.replaceRefreshToken(userUUID, refreshTokenUUID, oldRefreshTokenString, refreshTokenString);
        return new JsonWebTokenResponse(accessTokenString, refreshTokenEntity.getRefreshTokenUUID().toString());
    }
}