package org.cachewrapper.token.domain.token;

import java.util.UUID;

public record AccessToken(
        UUID userUUID,
        String username
) implements Token {}