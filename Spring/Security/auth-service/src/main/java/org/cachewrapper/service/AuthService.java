package org.cachewrapper.service;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.token.domain.payload.AccessTokenPayload;
import org.cachewrapper.token.domain.payload.RefreshTokenPayload;
import org.cachewrapper.token.service.AccessTokenService;
import org.cachewrapper.token.service.RefreshTokenService;
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
                .header("Set-Cookie", accessTokenCookie.toString(), refreshTokenCookie.toString())
                .build();
    }

    @NotNull
    default ResponseCookie generateCookie(
            @NotNull String identifier,
            @NotNull String value,
            @NotNull Duration expirationDuration
    ) {
        return ResponseCookie.from(identifier, value)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(expirationDuration)
                .sameSite("Lax")
                .build();
    }
}