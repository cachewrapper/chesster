package org.cachewrapper.token.domain.payload;

import java.util.UUID;

public record RefreshTokenPayload(
        UUID userUUID,
        String username
) implements TokenPayload {}