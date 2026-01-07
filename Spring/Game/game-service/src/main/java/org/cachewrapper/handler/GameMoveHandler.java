package org.cachewrapper.handler;

import org.cachewrapper.socket.payload.request.MoveRequestPayload;
import org.cachewrapper.socket.payload.response.MoveResponsePayload;

public class GameMoveHandler implements GameHandler<MoveResponsePayload, MoveRequestPayload> {

    @Override
    public MoveResponsePayload handle(MoveRequestPayload requestPayload) {
        return null;
    }
}