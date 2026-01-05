package org.cachewrapper.command.domain;

public record LoginUserCommand(
        String email,
        String password
) implements Command {}