package org.cachewrapper.piece.validator;

import org.cachewrapper.piece.data.Location;
import org.cachewrapper.piece.type.GamePiece;
import org.jetbrains.annotations.NotNull;

public interface GamePieceValidator {

    boolean validate(@NotNull Location previousLocation, @NotNull Location goalLocation, @NotNull GamePiece gamePiece);

    Class<? extends GamePiece> getPieceType();
}