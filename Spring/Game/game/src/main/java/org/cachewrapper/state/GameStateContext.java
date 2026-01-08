package org.cachewrapper.state;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.Game;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class GameStateContext {

    private final Game game;
    private GameState currentGameState;

    public void switchState() {
        if (currentGameState == null) {
            currentGameState = this.game.primaryGameState();
            initializeState();
            return;
        }

        final GameState nextGameState = currentGameState.metadata().nextGameState();
        if (nextGameState == null) {
            this.game.stop();
        } else {
            currentGameState = nextGameState;
            initializeState();
        }
    }

    public void initializeState() {
        final GameStateMetadata metadata = currentGameState.metadata();
        final Consumer<Game> startStateConsumer = metadata.startStateConsumer();

        startStateConsumer.accept(this.game);
    }
}