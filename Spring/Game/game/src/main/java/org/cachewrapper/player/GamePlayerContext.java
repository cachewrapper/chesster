package org.cachewrapper.player;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.player.data.GamePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class GamePlayerContext {

    private final Map<UUID, GamePlayer> players = new ConcurrentHashMap<>();

    public GamePlayer getGamePlayer(@NotNull UUID playerUUID) {
        return players.get(playerUUID);
    }

    @NotNull
    public void addPlayerState(@NotNull UUID playerUUID, @NotNull GamePlayer gamePlayerState) {
        players.put(playerUUID, gamePlayerState);
    }

    @NotNull
    @UnmodifiableView
    public Set<UUID> getPlayersUUID() {
        return Collections.unmodifiableSet(players.keySet());
    }
}