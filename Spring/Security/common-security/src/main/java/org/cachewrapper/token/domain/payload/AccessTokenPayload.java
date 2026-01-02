package org.cachewrapper.token.domain.payload;

import java.util.UUID;

public record AccessTokenPayload(
        UUID userUUID,
        String username
) implements TokenPayload {}