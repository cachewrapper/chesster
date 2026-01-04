package org.cachewrapper.command.domain;

import java.util.UUID;

public record RemoveRefreshTokenCommand(
        UUID userUUID,
        String refreshTokenString
) implements Command {}