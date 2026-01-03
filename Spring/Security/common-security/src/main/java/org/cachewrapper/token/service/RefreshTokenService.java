package org.cachewrapper.token.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.token.domain.payload.RefreshTokenPayload;
import org.cachewrapper.token.domain.token.RefreshToken;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements TokenService<RefreshToken, RefreshTokenPayload> {

    @Override
    public @NotNull String generateTokenString(@NotNull RefreshTokenPayload tokenPayload) {
        var expireDate = Instant.now().plus(getExpirationDuration());
        var userUUID = tokenPayload.userUUID();
        var username = tokenPayload.username();

        return Jwts.builder()
                .claim("userUUID", userUUID.toString())
                .claim("username", username)
                .issuedAt(new Date())
                .expiration(Date.from(expireDate))
                .signWith(SECRET_KEY)
                .compact();
    }

    @Override
    public boolean validateTokenString(@NotNull String tokenString) {
        try {
            Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(tokenString);
            return true;
        } catch (ExpiredJwtException exception) {
            return false;
        }
    }

    @Override
    public @NotNull RefreshToken getTokenFromString(@NotNull String tokenString) {
        var userUUIDString = getClaim(tokenString, claims -> claims.get("userUUID").toString());
        var username = getClaim(tokenString, claims -> claims.get("username").toString());
        var userUUID = UUID.fromString(userUUIDString);

        return new RefreshToken(userUUID, username);
    }

    @Override
    public @NotNull Duration getExpirationDuration() {
        return Duration.ofDays(365);
    }
}
