package org.cachewrapper.command.domain;

public record LogoutUserCommand(
        String accessTokenString,
        String refreshTokenString
) implements Command {}