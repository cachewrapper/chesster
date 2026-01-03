package org.cachewrapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.coordinator.UpdateRefreshTokenCommandCoordinator;
import org.cachewrapper.command.domain.UpdateRefreshTokenCommand;
import org.cachewrapper.exception.RefreshTokenInvalidException;
import org.cachewrapper.service.AuthService;
import org.cachewrapper.token.domain.payload.AccessTokenPayload;
import org.cachewrapper.token.domain.payload.RefreshTokenPayload;
import org.cachewrapper.token.service.AccessTokenService;
import org.cachewrapper.token.service.RefreshTokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService implements AuthService {

    private final UpdateRefreshTokenCommandCoordinator updateRefreshTokenCommandCoordinator;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    @NotNull
    public ResponseEntity<String> refreshToken(@NotNull String refreshTokenCookieString) {
        try {
            var refreshToken = refreshTokenService.getTokenFromString(refreshTokenCookieString);
            var userUUID = refreshToken.userUUID();

            var accessTokenPayload = new AccessTokenPayload(userUUID, refreshToken.username());
            var accessTokenString = accessTokenService.generateTokenString(accessTokenPayload);

            var refreshTokenPayload = new RefreshTokenPayload(userUUID, accessTokenString);
            var refreshTokenString = refreshTokenService.generateTokenString(refreshTokenPayload);

            var updateRefreshTokenCommand = new UpdateRefreshTokenCommand(userUUID, refreshTokenString);
            updateRefreshTokenCommandCoordinator.coordinate(updateRefreshTokenCommand);

            return generateTokensResponse(accessTokenService, refreshTokenService, accessTokenString, refreshTokenString);
        } catch (RefreshTokenInvalidException exception) {
            var accessTokenCookie = ResponseCookie.from("access_token", "").build();
            var refreshTokenCookie = ResponseCookie.from("refresh_token", "").build();

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .header("Set-Cookie", accessTokenCookie.toString(), refreshTokenCookie.toString())
                    .build();
        }
    }
}