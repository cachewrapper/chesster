package org.cachewrapper.command.domain;

import java.util.UUID;

public record RefreshTokenCommand(
        UUID refreshTokenUUID
) implements Command {}