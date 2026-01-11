package org.cachewrapper.state;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.Game;
import org.cachewrapper.state.runnable.impl.GameSwitchStateScheduler;
import org.cachewrapper.state.type.GameState;
import org.cachewrapper.state.type.GameStateMetadata;
import org.cachewrapper.state.type.impl.GameStartState;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.function.Consumer;

@Getter
@RequiredArgsConstructor
public class GameStateContext {

    private final Game game;
    private GameState currentGameState;

    public void switchGameState() {
        if (currentGameState == null) {
            final GameStartState gameStartState = new GameStartState();
            initializeGameState(gameStartState, gameStartState.metadata());
            return;
        }

        final GameStateMetadata stateMetadata = currentGameState.metadata();
        final GameState nextGameState = stateMetadata.nextState();

        if (nextGameState != null) {
            initializeGameState(nextGameState, stateMetadata);
        } else {
            game.stop();
        }
    }

    private void initializeGameState(@NotNull GameState gameState, @NotNull GameStateMetadata stateMetadata) {
        currentGameState = gameState;
        final Duration lastsDuration = stateMetadata.lastsDuration();

        if (lastsDuration != null) {
            final GameSwitchStateScheduler switchStateScheduler = new GameSwitchStateScheduler(game);
            switchStateScheduler.schedule(lastsDuration);
        }

        final Consumer<Game> startStateConsumer = stateMetadata.startStateConsumer();
        startStateConsumer.accept(game);
    }
}