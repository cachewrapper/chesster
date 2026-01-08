package org.cachewrapper.player.data.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.figure.GameFigure;
import org.cachewrapper.player.GamePlayerColor;
import org.cachewrapper.player.data.GamePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class GamePlayerActive extends GamePlayer {

    private final GamePlayerColor gamePlayerColor;
    private final List<GameFigure> capturedFigures = new ArrayList<>();

    public GamePlayerActive(@NotNull UUID playerUUID, @NotNull GamePlayerColor gamePlayerColor) {
        super(playerUUID);
        this.gamePlayerColor = gamePlayerColor;
    }
}