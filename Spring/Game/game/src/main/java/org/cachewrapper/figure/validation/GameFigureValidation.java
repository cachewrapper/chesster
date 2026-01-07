package org.cachewrapper.figure.validation;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.figure.GameFigure;
import org.cachewrapper.figure.GameFigureContext;
import org.cachewrapper.player.GamePlayerColor;
import org.cachewrapper.player.GamePlayerContext;
import org.cachewrapper.player.data.GamePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@RequiredArgsConstructor
public abstract class GameFigureValidation<T extends GameFigure> {

    private static final int CHESSBOARD_SIZE = 8;

    protected final GameFigureContext gameFigureContext;
    protected final GamePlayerContext gamePlayerContext;

    protected boolean isOpponent(@NotNull UUID playerUUID, @Nullable GameFigure gameFigure) {
        if (gameFigure == null) {
            return false;
        }

        final GamePlayer gamePlayer = gamePlayerContext.getGamePlayer(playerUUID);
        final GamePlayer targetPlayer = gamePlayerContext.getGamePlayer(gameFigure.getPlayerUUID());

        return gamePlayer != null && gamePlayer.getGamePlayerColor() != targetPlayer.getGamePlayerColor();
    }

    protected int getDirection(@NotNull UUID playerUUID) {
        final GamePlayer gamePlayer = gamePlayerContext.getGamePlayer(playerUUID);
        if (gamePlayer == null) {
            return 0;
        }

        return gamePlayer.getGamePlayerColor() == GamePlayerColor.WHITE ? 1 : -1;
    }

    protected int getRow(int cell) {
        return cell / CHESSBOARD_SIZE;
    }

    protected int getColumn(int cell) {
        return cell % CHESSBOARD_SIZE;
    }

    public abstract boolean validate(
            @NotNull UUID playerUUID,
            @NotNull int primaryCell,
            @NotNull int finalCell,
            @NotNull T figure,
            @Nullable Integer capturedFigureCell
    );
}
