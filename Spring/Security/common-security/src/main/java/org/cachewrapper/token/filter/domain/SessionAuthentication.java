package org.cachewrapper.token.filter.domain;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;
import java.util.UUID;

@Getter
public class SessionAuthentication extends AbstractAuthenticationToken {

    private final UUID userUUID;
    private final String username;

    private final String accessTokenString;
    private final String refreshTokenString;

    public SessionAuthentication(
            @NotNull UUID userUUID,
            @NotNull String username,
            @NotNull String accessTokenString,
            @NotNull String refreshTokenString
    ) {
        super(Collections.emptyList());
        this.userUUID = userUUID;
        this.username = username;
        this.accessTokenString = accessTokenString;
        this.refreshTokenString = refreshTokenString;
    }

    @Override
    public @Nullable String getCredentials() {
        return accessTokenString;
    }

    @Override
    public @Nullable SessionAuthentication getPrincipal() {
        return this;
    }
}
