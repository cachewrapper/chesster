package org.cachewrapper.tracker;

import org.cachewrapper.Game;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameTracker {

    private final Map<UUID, Game> games = new ConcurrentHashMap<>();

    public void addGame(@NotNull UUID gameUUID, @NotNull Game game) {
        games.put(gameUUID, game);
    }

    public void removeGame(@NotNull UUID gameUUID) {
        games.remove(gameUUID);
    }

    public Game getGame(@NotNull UUID gameUUID) {
        return games.get(gameUUID);
    }

    @NotNull
    @UnmodifiableView
    public List<Game> getAllGames() {
        return games.values().stream().toList();
    }
}
