package org.cachewrapper.state.type.impl;

import org.cachewrapper.Game;
import org.cachewrapper.player.tracker.GamePlayerTracker;
import org.cachewrapper.player.type.GamePlayer;
import org.cachewrapper.player.type.impl.GamePlayerActive;
import org.cachewrapper.state.type.GameState;
import org.cachewrapper.state.type.GameStateMetadata;

import java.util.function.Consumer;

public class GameActiveState implements GameState {

    @Override
    public GameStateMetadata metadata() {
        return GameStateMetadata.builder()
                .startStateConsumer(startStateConsumer())
                .build();
    }

    private Consumer<Game> startStateConsumer() {
        return game -> {
            System.out.println("Starting game active state");
//            GamePlayerTracker playerTracker = game.getPlayerContext().getPlayerTracker();
//            List<GamePlayer> gamePlayerList = playerTracker.getGamePlayersByType(GamePlayerActive.class);
//
//            if (gamePlayerList.isEmpty() || gamePlayerList.size() < 2) {
//                System.out.println("No game players found");
//                game.stop();
//            }
//
//            System.out.println("Game started");
//            game.getBoardContext().loadGameBoard();
        };
    }
}
