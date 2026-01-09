package org.cachewrapper.board;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.Game;
import org.cachewrapper.board.tracker.GameBoardPieceTracker;
import org.cachewrapper.piece.data.Location;
import org.cachewrapper.piece.game.type.GamePiece;
import org.cachewrapper.piece.game.validator.GamePieceValidator;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class GameBoardContext {

    private final GameBoardPieceTracker boardPieceTracker = new GameBoardPieceTracker();
    private final Game game;

    public void loadGameBoard() {

    }

    public boolean moveGamePiece(@NotNull Location previousLocation, @NotNull Location goalLocation) {
        final GamePiece gamePiece = boardPieceTracker.getPiece(previousLocation);
        if (gamePiece == null) {
            return false;
        }

        final Class<? extends GamePiece> gamePieceType = gamePiece.getClass();
        final GamePieceValidator pieceValidator = game.getPieceContext().getPieceValidator(gamePieceType);

        final boolean isMoveValid = pieceValidator.validate(previousLocation, goalLocation, gamePiece);
        if (!isMoveValid) {
            return false;
        }

        this.updatePieceLocation(previousLocation, goalLocation);
        return true;
    }

    private void updatePieceLocation(@NotNull Location previousLocation, @NotNull Location goalLocation) {
        final GamePiece gamePiece = boardPieceTracker.removePieceLocation(previousLocation);
        boardPieceTracker.addPieceLocation(previousLocation, gamePiece);
    }
}