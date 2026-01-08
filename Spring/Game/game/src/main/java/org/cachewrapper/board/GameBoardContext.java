package org.cachewrapper.board;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.Game;
import org.cachewrapper.figure.GameFigure;
import org.cachewrapper.figure.GameFigureContext;
import org.cachewrapper.figure.impl.PawnGameFigure;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@RequiredArgsConstructor
public class GameBoardContext {

    private final Game game;

    public void loadDefaultBoard() {
        final GameFigureContext gameFigureContext = game.getFigureContext();
        final List<GameFigure> gameFigures = gameFigureContext.loadGameFigures();

//        gameFigureContext.getGameFigureTracker().setGameFigureCell();
    }

}