package org.cachewrapper.token.domain.token;

import java.util.UUID;

public record RefreshToken(
        UUID userUUID,
        String username
) implements Token {}