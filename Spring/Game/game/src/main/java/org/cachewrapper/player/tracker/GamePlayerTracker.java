package org.cachewrapper.player.tracker;

import org.cachewrapper.player.type.GamePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GamePlayerTracker {

    private final Map<UUID, GamePlayer> gamePlayers = new ConcurrentHashMap<>();

    public void trackPlayer(@NotNull GamePlayer gamePlayer) {
        final UUID playerUUID = gamePlayer.getPlayerUUID();
        gamePlayers.put(playerUUID, gamePlayer);
    }

    @SuppressWarnings("unchecked")
    public <T extends GamePlayer> T getGamePlayer(@NotNull UUID playerUUID) {
        return (T) gamePlayers.get(playerUUID);
    }

    public void untrackPlayer(@NotNull GamePlayer gamePlayer) {
        final UUID playerUUID = gamePlayer.getPlayerUUID();
        gamePlayers.remove(playerUUID);
    }

    @NotNull
    @UnmodifiableView
    public List<GamePlayer> getGamePlayers() {
        return gamePlayers.values().stream().toList();
    }
}
