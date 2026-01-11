package org.cachewrapper.piece.type.impl;

import org.cachewrapper.piece.type.GamePiece;

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
