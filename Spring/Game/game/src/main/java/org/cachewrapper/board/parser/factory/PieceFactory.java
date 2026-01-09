package org.cachewrapper.board.parser.factory;

import org.cachewrapper.piece.game.type.GamePiece;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface PieceFactory<P extends GamePiece> {
    P createPiece(@NotNull UUID playerUUID);
}