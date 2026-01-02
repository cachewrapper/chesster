package org.cachewrapper.token.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.cachewrapper.token.domain.payload.TokenPayload;
import org.cachewrapper.token.domain.token.Token;
import org.jetbrains.annotations.NotNull;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.function.Function;

public interface TokenService<T extends Token, P extends TokenPayload> {

    SecretKey SECRET_KEY = Keys.hmacShaKeyFor("CHESSTER_CHESSTER_CHESSTER_CHESSTER_CHESSTER".getBytes());

    @NotNull
    String generateTokenString(@NotNull P tokenPayload);

    boolean validateTokenString(@NotNull String tokenString);

    @NotNull
    T getTokenFromString(@NotNull String tokenString);

    @NotNull
    <C> C getClaim(@NotNull String tokenString, @NotNull Function<Claims, C> resolver);

    @NotNull
    Duration getExpirationDuration();
}