package org.cachewrapper.figure.validation.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.figure.GameFigure;
import org.cachewrapper.figure.GameFigureContext;
import org.cachewrapper.figure.impl.PawnFigure;
import org.cachewrapper.figure.validation.GameFigureValidation;
import org.cachewrapper.player.GamePlayerColor;
import org.cachewrapper.player.GamePlayerContext;
import org.cachewrapper.player.data.GamePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PawnFigureValidation extends GameFigureValidation<PawnFigure> {

    public PawnFigureValidation(GameFigureContext gameFigureContext, GamePlayerContext gamePlayerContext) {
        super(gameFigureContext, gamePlayerContext);
    }

    @Override
    public boolean validate(
            @NotNull UUID playerUUID,
            @NotNull int primaryCell,
            @NotNull int finalCell,
            @NotNull PawnFigure figure,
            @Nullable Integer capturedFigureCell
    ) {
        final int direction = getDirection(playerUUID);

        final int primaryRow = getRow(primaryCell);
        final int primaryColumn = getColumn(primaryCell);

        final int finalRow = getRow(finalCell);
        final int finalColumn = getColumn(finalCell);

        final int rowDifference = finalRow - primaryRow;
        final int columnDifference = finalColumn - primaryColumn;

        final GameFigure targetGameFigure = capturedFigureCell != null ? gameFigureContext.getFigure(capturedFigureCell) : null;
        if (targetGameFigure != null) {
            return isOpponent(playerUUID, targetGameFigure) && Math.abs(columnDifference) == 1 && rowDifference == direction;
        }

        if (columnDifference == 0 && rowDifference == direction) {
            return true;
        }

        int startRow = direction == 1 ? 1 : 6;
        return columnDifference == 0 && rowDifference == 2 * direction && primaryRow == startRow;
    }
}