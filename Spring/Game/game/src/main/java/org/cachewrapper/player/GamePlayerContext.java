package org.cachewrapper.player;

import org.cachewrapper.player.tracker.GamePlayerTracker;
import org.cachewrapper.player.type.impl.GamePlayerActive;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GamePlayerContext {

    private final GamePlayerTracker playerTracker = new GamePlayerTracker();

    public void addActiveGamePlayer(@NotNull UUID playerUUID) {
        final int gamePlayerListSize  = playerTracker.getGamePlayersByType(GamePlayerActive.class).size();
        final GamePlayerActive gamePlayerActive = gamePlayerListSize == 0
                ? new GamePlayerActive(playerUUID, GamePlayerColor.WHITE)
                : new GamePlayerActive(playerUUID, GamePlayerColor.BLACK);

        playerTracker.trackPlayer(gamePlayerActive);
    }

    public int getGamePlayersActiveSize() {
        return playerTracker.getGamePlayersByType(GamePlayerActive.class).size();
    }
}