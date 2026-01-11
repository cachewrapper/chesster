package org.cachewrapper.piece.validator.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.Game;
import org.cachewrapper.piece.data.Location;
import org.cachewrapper.piece.type.GamePiece;
import org.cachewrapper.piece.type.impl.PawnGamePiece;
import org.cachewrapper.piece.validator.GamePieceValidator;
import org.cachewrapper.player.GamePlayerColor;
import org.cachewrapper.player.tracker.GamePlayerTracker;
import org.cachewrapper.player.type.GamePlayer;
import org.cachewrapper.player.type.impl.GamePlayerActive;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@RequiredArgsConstructor
public class PawnPieceValidator implements GamePieceValidator {

    private final Game game;

    @Override
    public boolean validate(
            @NotNull Location previousLocation,
            @NotNull Location goalLocation,
            @NotNull GamePiece gamePiece
    ) {
        final UUID playerUUID = gamePiece.getPlayerUUID();
//        final GamePlayerActive gamePlayerActive = game.getPlayerContext().getPlayerTracker().getGamePlayer(playerUUID);
//
//        final boolean isPieceWhite = gamePlayerActive.getPlayerColor() == GamePlayerColor.WHITE;
//        final int direction = isPieceWhite ? -1 : 1;
//
//        final int deltaX = goalLocation.coordinateX() - previousLocation.coordinateX();
//        final int deltaY = goalLocation.coordinateY() - previousLocation.coordinateY();
//
//        final GamePiece capturingGamePeace = game.getBoardContext().getBoardPieceTracker().getPiece(goalLocation);
//        if (deltaX == 0 && deltaY == direction && capturingGamePeace == null) {
//            return true;
//        }
//
//        if (deltaX == 0 && deltaY == 2 * direction && isFirstMove(previousLocation, gamePiece)) {
//            final int coordinateX = previousLocation.coordinateX();
//            final int coordinateY = previousLocation.coordinateY() + direction;
//
//            final Location middleLocation = new Location(coordinateX, coordinateY);
//            final GamePiece middleGamePiece = game.getBoardContext().getBoardPieceTracker().getPiece(middleLocation);
//
//            if (middleGamePiece == null && capturingGamePeace == null) {
//                return true;
//            }
//        }
//
//        boolean isCaptured = Math.abs(deltaX) == 1 && deltaY == direction
//                && capturingGamePeace != null
//                && !capturingGamePeace.getPlayerUUID().equals(playerUUID);
//
//        if (isCaptured) {
//            return true;
//        }

        return true;
    }

    @Override
    public Class<? extends GamePiece> getPieceType() {
        return PawnGamePiece.class;
    }

    private boolean isFirstMove(@NotNull Location previousLocation, @NotNull GamePiece gamePiece) {
//        final UUID playerUUID = gamePiece.getPlayerUUID();
//        final GamePlayerActive gamePlayer = game.getPlayerContext().getPlayerTracker().getGamePlayer(playerUUID);
//
//        int possibleLocationY = gamePlayer.getPlayerColor() == GamePlayerColor.WHITE ? 6 : 1;
//        return previousLocation.coordinateY() == possibleLocationY;
        return true;
    }
}