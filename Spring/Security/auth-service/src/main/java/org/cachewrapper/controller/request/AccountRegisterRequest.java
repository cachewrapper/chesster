package org.cachewrapper.controller.request;

public record AccountRegisterRequest(
        String email,
        String username,
        String password
) {}