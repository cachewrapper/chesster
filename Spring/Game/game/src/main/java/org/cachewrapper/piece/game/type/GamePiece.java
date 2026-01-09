package org.cachewrapper.piece.game.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public abstract class GamePiece {

    private final UUID playerUUID;

    public abstract String identifier();
}