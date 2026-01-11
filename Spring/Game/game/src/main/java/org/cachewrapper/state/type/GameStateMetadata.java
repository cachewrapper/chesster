package org.cachewrapper.state.type;

import jakarta.validation.constraints.Null;
import lombok.Builder;
import org.cachewrapper.Game;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.function.Consumer;

@Builder
public record GameStateMetadata(
        @NotNull
        Consumer<Game> startStateConsumer,
        @Nullable
        Duration lastsDuration,
        @Nullable
        GameState nextState,
        @Nullable
        Consumer<Game> endStateConsumer
) {}