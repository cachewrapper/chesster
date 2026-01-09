package org.cachewrapper.player.type.impl;

import lombok.Getter;
import org.cachewrapper.player.GamePlayerColor;
import org.cachewrapper.player.type.GamePlayer;

import java.util.UUID;

@Getter
public class GamePlayerActive extends GamePlayer {

    private final GamePlayerColor playerColor;

    public GamePlayerActive(UUID playerUUID, GamePlayerColor playerColor) {
        super(playerUUID);
        this.playerColor = playerColor;
    }
}
