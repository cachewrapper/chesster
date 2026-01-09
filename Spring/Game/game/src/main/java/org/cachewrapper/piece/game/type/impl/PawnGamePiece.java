package org.cachewrapper.piece.game.type.impl;

import org.cachewrapper.piece.game.type.GamePiece;

import java.util.UUID;

public class PawnGamePiece extends GamePiece {

    public PawnGamePiece(UUID playerUUID) {
        super(playerUUID);
    }

    @Override
    public String identifier() {
        return "pawn";
    }
}
