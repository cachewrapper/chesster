package org.cachewrapper.exception;

import org.jetbrains.annotations.NotNull;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(@NotNull String username) {
        super("Username already exists: " + username);
    }
}