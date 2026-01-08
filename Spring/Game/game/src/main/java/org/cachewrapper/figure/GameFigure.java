package org.cachewrapper.figure;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
public abstract class GameFigure {

    private final UUID playerUUID;

    private final int column;
    private final int row;

    public GameFigure(@NotNull UUID playerUUID, int column, int row) {
        this.playerUUID = playerUUID;

        this.column = column;
        this.row = row;
    }

    public abstract String identifier();
}