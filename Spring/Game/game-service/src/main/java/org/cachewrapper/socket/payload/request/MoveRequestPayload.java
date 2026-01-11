package org.cachewrapper.socket.payload.request;

import org.cachewrapper.piece.data.Location;

public record MoveRequestPayload(
        Location previousLocation,
        Location goalLocation
) implements RequestPayload {}