package org.cachewrapper.figure;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class GameFigureContext {

    private final Map<Integer, GameFigure> figureCells = new HashMap<>();

    public <F extends GameFigure> void setFigureCell(@NotNull int cell, @NotNull F figure) {
        figureCells.put(cell, figure);
    }

    @NotNull
    public GameFigure getFigure(@NotNull int cell) {
        return figureCells.get(cell);
    }

    @NotNull
    @UnmodifiableView
    public Map<Integer, GameFigure> getFigureCells() {
        return Collections.unmodifiableMap(figureCells);
    }
}