package org.cachewrapper.state;

import lombok.Builder;
import org.cachewrapper.Game;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@Builder
public record GameStateMetadata(
        @NotNull
        Consumer<Game> startStateConsumer,
        @Nullable
        GameState nextGameState,
        @NotNull
        Consumer<Game> endStateConsumer
) {}