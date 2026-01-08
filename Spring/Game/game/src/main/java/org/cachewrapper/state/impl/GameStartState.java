package org.cachewrapper.state.impl;

import org.cachewrapper.state.GameState;
import org.cachewrapper.state.GameStateMetadata;

public class GameStartState implements GameState {

    @Override
    public GameStateMetadata metadata() {
        return GameStateMetadata.builder()
                .build();
    }
}