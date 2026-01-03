package org.cachewrapper.command.domain;

import java.util.UUID;

public record UpdateRefreshTokenCommand(
        UUID userUUID,
        String refreshTokenString
) implements Command {}