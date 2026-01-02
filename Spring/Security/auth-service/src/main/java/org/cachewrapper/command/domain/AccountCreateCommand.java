package org.cachewrapper.command.domain;

import java.util.UUID;

public record AccountCreateCommand(
        UUID userUUID,
        String email,
        String username,
        String password
) implements Command {}