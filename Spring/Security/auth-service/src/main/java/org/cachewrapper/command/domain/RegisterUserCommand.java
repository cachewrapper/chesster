package org.cachewrapper.command.domain;

public record RegisterUserCommand(
        String email,
        String username,
        String password
) implements Command {}