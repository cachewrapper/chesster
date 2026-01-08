package org.cachewrapper.figure.tracker;

import org.cachewrapper.board.validation.GameFigureValidation;
import org.cachewrapper.figure.GameFigure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class GameFigureTracker {

    private final Map<Integer, GameFigure> figureCells = new HashMap<>();

    public <F extends GameFigure> void setGameFigureCell(
            @NotNull int cell,
            @NotNull F figure
    ) {
        figureCells.put(cell, figure);
    }

    @NotNull
    public GameFigure geGameFigure(@NotNull int cell) {
        return figureCells.get(cell);
    }

    @NotNull
    @UnmodifiableView
    public Map<Integer, GameFigure> getGameFigureCells() {
        return Collections.unmodifiableMap(figureCells);
    }
}