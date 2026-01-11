package org.cachewrapper.socket.payload.response;

import org.cachewrapper.piece.data.Location;
import org.springframework.http.HttpStatus;

public record MoveResponsePayload(
        Location previousLocation,
        Location goalLocation,
        HttpStatus httpStatus
) implements ResponsePayload {}