package org.cachewrapper.board.handler.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.Game;
import org.cachewrapper.board.handler.GameBoardHandler;
import org.cachewrapper.board.handler.payload.HandleMovePayload;
import org.cachewrapper.board.tracker.GameBoardPieceTracker;
import org.cachewrapper.piece.data.Location;
import org.cachewrapper.piece.type.GamePiece;
import org.cachewrapper.piece.validator.GamePieceValidator;

@RequiredArgsConstructor
public class GameMoveHandler implements GameBoardHandler<HandleMovePayload> {

    private final Game game;
    private final GameBoardPieceTracker boardPieceTracker;

    @Override
    public boolean handle(HandleMovePayload payload) {
        final Location previousLocation = payload.previousLocation();
        final Location goalLocation = payload.goalLocation();
        final GamePiece gamePiece = payload.gamePiece();

        if (gamePiece == null) {
            return false;
        }

        final Class<? extends GamePiece> gamePieceType = gamePiece.getClass();
        final GamePieceValidator pieceValidator = game.getPieceContext().getPieceValidator(gamePieceType);

        final boolean isMoveValid = pieceValidator.validate(previousLocation, goalLocation, gamePiece);
        if (!isMoveValid) {
            return false;
        }

        if (boardPieceTracker.getPiece(goalLocation) != null) {
            boardPieceTracker.removePieceLocation(goalLocation);
        }

        this.boardPieceTracker.removePieceLocation(previousLocation);
        this.boardPieceTracker.addPieceLocation(goalLocation, gamePiece);
        return true;
    }
}
