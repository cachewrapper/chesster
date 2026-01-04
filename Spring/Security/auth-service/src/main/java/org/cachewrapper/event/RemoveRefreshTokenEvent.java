package org.cachewrapper.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class RemoveRefreshTokenEvent implements Event {
    private final UUID userUUID;
    private final String refreshTokenString;
}