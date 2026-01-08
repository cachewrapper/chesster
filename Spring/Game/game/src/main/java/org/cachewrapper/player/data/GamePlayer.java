package org.cachewrapper.player.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public abstract class GamePlayer {

    private final UUID playerUUID;
}