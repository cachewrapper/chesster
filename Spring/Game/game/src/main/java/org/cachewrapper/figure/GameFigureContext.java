package org.cachewrapper.figure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.Game;
import org.cachewrapper.figure.impl.PawnGameFigure;
import org.cachewrapper.figure.tracker.GameFigureTracker;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class GameFigureContext {

    private static final String FEN_STRING = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    private final GameFigureTracker gameFigureTracker = new GameFigureTracker();
    private final Game game;

    @NotNull
    public List<GameFigure> loadGameFigures() {
        final List<GameFigure> gameFigures = new ArrayList<>();

        final String fenPosition = FEN_STRING.split(" ")[0];
        final String[] rows = fenPosition.split("/");

        for (int coordianteY = 0; coordianteY < 8; coordianteY++) {
            int coordinateX = 0;

            for (char character : rows[coordianteY].toCharArray()) {
                if (Character.isDigit(character)) {
                    coordinateX += character - '0';
                    continue;
                }

                UUID figureOwnerUUID = Character.isUpperCase(character)
                        ? game.getWhitePlayerUUID()
                        : game.getBlackPlayerUUID();

                GameFigure figure = switch (Character.toUpperCase(character)) {
                    case 'P' -> new PawnGameFigure(figureOwnerUUID, coordinateX, coordianteY);
                    default -> null;
                };

                gameFigures.add(figure);
                coordinateX++;
            }
        }

        return gameFigures;
    }
}