package org.cachewrapper.player.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public abstract class GamePlayer {
    private final UUID playerUUID;
}
