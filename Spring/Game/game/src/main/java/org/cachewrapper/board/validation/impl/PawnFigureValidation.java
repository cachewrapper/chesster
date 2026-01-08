package org.cachewrapper.board.validation.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.board.validation.GameFigureValidation;
import org.cachewrapper.figure.GameFigure;
import org.cachewrapper.figure.impl.PawnGameFigure;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PawnFigureValidation extends GameFigureValidation {

    @Override
    public boolean validate(@NotNull UUID playerUUID, @NotNull int previousCell, @NotNull int destinationCell) {
        return false;
    }

    @Override
    public Class<? extends GameFigure> figureType() {
        return PawnGameFigure.class;
    }
}