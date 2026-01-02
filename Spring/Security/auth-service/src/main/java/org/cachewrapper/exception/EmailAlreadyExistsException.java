package org.cachewrapper.exception;

import org.jetbrains.annotations.NotNull;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(@NotNull String email) {
        super("Email already exists: " + email);
    }
}