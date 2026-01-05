package org.cachewrapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.domain.RefreshTokenCommand;
import org.cachewrapper.command.response.JsonWebTokenResponse;
import org.cachewrapper.command.service.RefreshTokenCommandService;
import org.cachewrapper.exception.RefreshTokenInvalidException;
import org.cachewrapper.exception.RefreshTokenNotFoundException;
import org.cachewrapper.service.AuthService;
import org.cachewrapper.token.filter.domain.SessionAuthentication;
import org.cachewrapper.token.service.token.AccessTokenService;
import org.cachewrapper.token.service.token.RefreshTokenService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService implements AuthService {

    private final RefreshTokenCommandService refreshTokenCommandService;

    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    @NotNull
    public ResponseEntity<String> refreshToken(@NotNull UUID refreshTokenUUID) {
        try {
            final RefreshTokenCommand refreshTokenCommand = new RefreshTokenCommand(refreshTokenUUID);
            final JsonWebTokenResponse jsonWebTokenResponse = refreshTokenCommandService.execute(refreshTokenCommand);

            final String accessTokenString = jsonWebTokenResponse.accessTokenString();
            final String refreshTokenUUIDString = jsonWebTokenResponse.refreshTokenUUIDString();

            return generateTokensResponse(accessTokenService, refreshTokenService, accessTokenString, refreshTokenUUIDString);
        } catch (RefreshTokenInvalidException | RefreshTokenNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @NotNull
    public ResponseEntity<String> validateAccessToken(@Nullable SessionAuthentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final String accessTokenString = authentication.getAccessTokenString();
        final boolean isAccessTokenValid = accessTokenService.validateTokenString(accessTokenString);
        if (!isAccessTokenValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}