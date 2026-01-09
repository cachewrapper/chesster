package org.cachewrapper.board.parser.factory.impl;

import org.cachewrapper.board.parser.factory.PieceFactory;
import org.cachewrapper.piece.game.type.impl.PawnGamePiece;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PawnPieceFactory implements PieceFactory<PawnGamePiece> {

    @Override
    public PawnGamePiece createPiece(@NotNull UUID playerUUID) {
        return new PawnGamePiece(playerUUID);
    }
}
