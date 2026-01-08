package org.cachewrapper;

import lombok.Getter;
import org.cachewrapper.board.GameBoardContext;
import org.cachewrapper.figure.GameFigureContext;
import org.cachewrapper.state.GameState;
import org.cachewrapper.state.GameStateContext;

import java.util.UUID;

@Getter
public abstract class Game {

    private final UUID whitePlayerUUID;
    private final UUID blackPlayerUUID;

    private final GameStateContext stateContext;
    private final GameFigureContext figureContext;
    private final GameBoardContext boardContext;

    public Game(final UUID whitePlayerUUID, final UUID blackPlayerUUID) {
        this.whitePlayerUUID = whitePlayerUUID;
        this.blackPlayerUUID = blackPlayerUUID;

        this.stateContext = new GameStateContext(this);
        this.figureContext = new GameFigureContext(this);
        this.boardContext = new GameBoardContext(this);
    }

    public void start() {
        stateContext.switchState();
    }

    public void stop() {

    }

    public abstract GameState primaryGameState();
}