package org.cachewrapper.board.handler;

import org.cachewrapper.board.handler.payload.HandlePayload;

public interface GameBoardHandler<P extends HandlePayload> {
    boolean handle(P payload);
}
