package org.cachewrapper.board.handler.payload;

import org.cachewrapper.piece.data.Location;
import org.cachewrapper.piece.type.GamePiece;

public record HandleMovePayload(
        Location previousLocation,
        Location goalLocation,
        GamePiece gamePiece
) implements HandlePayload {}