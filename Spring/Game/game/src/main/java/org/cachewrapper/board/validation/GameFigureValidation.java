package org.cachewrapper.board.validation;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.figure.GameFigure;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@RequiredArgsConstructor
public abstract class GameFigureValidation {

    private static final int CHESSBOARD_SIZE = 8;

    public abstract boolean validate(
            @NotNull UUID playerUUID,
            @NotNull int previousCell,
            @NotNull int destinationCell
    );

    public abstract Class<? extends GameFigure> figureType();
}
