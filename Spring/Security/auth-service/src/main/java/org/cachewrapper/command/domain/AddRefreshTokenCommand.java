package org.cachewrapper.command.domain;

import java.util.UUID;

public record AddRefreshTokenCommand(
        UUID userUUID,
        String refreshTokenString
) implements Command {}