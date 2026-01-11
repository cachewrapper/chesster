package org.cachewrapper.piece.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.piece.data.Location;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public abstract class GamePiece {

    private final UUID playerUUID;

    public abstract String identifier();
}