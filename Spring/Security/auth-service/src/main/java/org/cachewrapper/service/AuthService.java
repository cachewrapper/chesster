package org.cachewrapper.service;

import org.cachewrapper.token.service.token.AccessTokenService;
import org.cachewrapper.token.service.token.RefreshTokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import java.time.Duration;

public interface AuthService {

    @NotNull
    default ResponseEntity<String> generateTokensResponse(
            @NotNull AccessTokenService accessTokenService,
            @NotNull RefreshTokenService refreshTokenService,
            @NotNull String accessTokenString,
            @NotNull String refreshTokenString
    ) {
        var accessTokenExpiration = accessTokenService.getExpirationDuration();
        var accessTokenCookie = generateCookie("access_token", accessTokenString, accessTokenExpiration);

        var refreshTokenExpiration = refreshTokenService.getExpirationDuration();
        var refreshTokenCookie = generateCookie("refresh_token", refreshTokenString, refreshTokenExpiration);

        return ResponseEntity.ok()
                .header("Set-Cookie", accessTokenCookie.toString())
                .header("Set-Cookie", refreshTokenCookie.toString())
                .build();
    }

    @NotNull
    default ResponseCookie generateCookie(
            @NotNull String identifier,
            @NotNull String value,
            @NotNull Duration expirationDuration
    ) {
        return ResponseCookie.from(identifier, value)
                .httpOnly(false)
                .secure(false)
                .path("/")
                .maxAge(expirationDuration)
                .sameSite("Lax")
                .build();
    }
}