package org.cachewrapper;

import lombok.Getter;
import org.cachewrapper.board.GameBoardContext;
import org.cachewrapper.event.GameEventContext;
import org.cachewrapper.mechanic.GameMechanicContext;
import org.cachewrapper.piece.GamePieceContext;
import org.cachewrapper.player.GamePlayerContext;
import org.cachewrapper.state.GameStateContext;

@Getter
public abstract class Game {

    private final GameBoardContext boardContext;
    private final GamePieceContext pieceContext;
    private final GamePlayerContext playerContext;
    private final GameStateContext stateContext;
    private final GameEventContext eventContext;
    private final GameMechanicContext mechanicContext;

    protected Game() {
        this.boardContext = new GameBoardContext(this);
        this.pieceContext = new GamePieceContext(this);
        this.playerContext = new GamePlayerContext();
        this.stateContext = new GameStateContext(this);
        this.eventContext = new GameEventContext();
        this.mechanicContext = new GameMechanicContext(this);
    }

    public void start() {
        stateContext.switchGameState();
        System.out.println("Game started");
    }

    public void stop() {
        System.out.println("Stopping game");
    }
}