package org.cachewrapper.state.type.impl;

import org.cachewrapper.Game;
import org.cachewrapper.event.GameEventContext;
import org.cachewrapper.mechanic.GameMechanicContext;
import org.cachewrapper.mechanic.type.impl.GameJoinMechanic;
import org.cachewrapper.state.type.GameState;
import org.cachewrapper.state.type.GameStateMetadata;

import java.time.Duration;
import java.util.function.Consumer;

public class GameStartState implements GameState {

    @Override
    public GameStateMetadata metadata() {
        return GameStateMetadata.builder()
                .startStateConsumer(startStateConsumer())
                .lastsDuration(Duration.ofSeconds(10))
                .nextState(new GameActiveState())
                .build();
    }

    private Consumer<Game> startStateConsumer() {
        return game -> {
            final GameMechanicContext mechanicContext = game.getMechanicContext();
            mechanicContext.enable(new GameJoinMechanic(game));
        };
    }
}
