package org.cachewrapper.state.runnable.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.Game;
import org.cachewrapper.state.GameStateContext;
import org.cachewrapper.state.runnable.GameScheduler;
import org.cachewrapper.state.type.GameState;
import org.cachewrapper.state.type.GameStateMetadata;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class GameSwitchStateScheduler implements GameScheduler {

    private final Game game;

    @Override
    public void schedule(@NotNull Duration duration) {
        SCHEDULER.schedule(() -> {
            final GameStateContext stateContext = game.getStateContext();
            final GameState currentState = stateContext.getCurrentGameState();

            final GameStateMetadata metadata = currentState.metadata();
            final Consumer<Game> stateEndConsumer = metadata.endStateConsumer();

            if (stateEndConsumer != null) {
                stateEndConsumer.accept(game);
            }

            stateContext.switchGameState();
        }, duration.toSeconds(), TimeUnit.SECONDS);
    }
}
