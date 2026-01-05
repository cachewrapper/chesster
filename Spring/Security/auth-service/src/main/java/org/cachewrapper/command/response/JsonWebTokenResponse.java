package org.cachewrapper.command.response;

public record JsonWebTokenResponse(
        String accessTokenString,
        String refreshTokenUUIDString
) {}