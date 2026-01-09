package org.cachewrapper.piece.game.validator.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.Game;
import org.cachewrapper.piece.data.Location;
import org.cachewrapper.piece.game.type.GamePiece;
import org.cachewrapper.piece.game.type.impl.PawnGamePiece;
import org.cachewrapper.piece.game.validator.GamePieceValidator;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class PawnPieceValidator implements GamePieceValidator {

    private final Game game;

    @Override
    public boolean validate(
            @NotNull Location previousLocation,
            @NotNull Location goalLocation,
            @NotNull GamePiece gamePiece
    ) {
        return true;
    }

    @Override
    public Class<? extends GamePiece> getPieceType() {
        return PawnGamePiece.class;
    }
}