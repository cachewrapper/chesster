package org.cachewrapper.socket.payload.request;

import java.util.UUID;

public record JoinGameRequestPayload(
        UUID gameUUID
) implements RequestPayload {}