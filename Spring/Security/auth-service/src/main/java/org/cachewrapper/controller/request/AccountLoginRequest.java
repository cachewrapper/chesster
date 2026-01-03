package org.cachewrapper.controller.request;

public record AccountLoginRequest(
        String email,
        String password
) {}