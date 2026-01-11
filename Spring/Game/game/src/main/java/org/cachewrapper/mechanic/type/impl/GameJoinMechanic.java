package org.cachewrapper.mechanic.type.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.Game;
import org.cachewrapper.event.GameEventContext;
import org.cachewrapper.event.GameEventListenerData;
import org.cachewrapper.event.type.GameJoinEvent;
import org.cachewrapper.mechanic.type.GameMechanic;

import java.util.UUID;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class GameJoinMechanic implements GameMechanic<GameJoinEvent> {

    private final Game game;

    @Override
    public void enable() {
        final GameEventContext eventContext = new GameEventContext();
        final var eventListenerData = GameEventListenerData.<GameJoinEvent>builder()
                .eventAction(eventListener())
                .eventType(GameJoinEvent.class)
                .build();

        eventContext.register(eventListenerData);
    }

    @Override
    public Class<GameJoinEvent> getEventType() {
        return GameJoinEvent.class;
    }

    private Consumer<GameJoinEvent> eventListener() {
        return event -> {
            System.out.println("Game Join Event");
            final UUID playerUUID = event.getPlayerUUID();
            game.getPlayerContext().addActiveGamePlayer(playerUUID);

            final int gamePlayersActiveSize = game.getPlayerContext().getGamePlayersActiveSize();
            if (gamePlayersActiveSize >= 2) {
                System.out.println("Game Players Active Size: " + gamePlayersActiveSize);
                game.getStateContext().switchGameState();
            }
        };
    }
}