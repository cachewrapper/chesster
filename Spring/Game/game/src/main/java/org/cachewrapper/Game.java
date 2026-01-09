package org.cachewrapper;

import lombok.Getter;
import org.cachewrapper.board.GameBoardContext;
import org.cachewrapper.piece.GamePieceContext;
import org.cachewrapper.player.GamePlayerContext;

@Getter
public abstract class Game {

    private final GameBoardContext boardContext;
    private final GamePieceContext pieceContext;
    private final GamePlayerContext playerContext;

    public Game() {
        boardContext = new GameBoardContext(this);
        pieceContext = new GamePieceContext(this);
        playerContext = new GamePlayerContext();
    }

    public void start() {

    }

    public void stop() {

    }
}