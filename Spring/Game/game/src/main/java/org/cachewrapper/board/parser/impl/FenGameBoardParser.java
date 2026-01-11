package org.cachewrapper.board.parser.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.board.parser.GameBoardParser;
import org.cachewrapper.board.parser.factory.PieceFactoryFacade;
import org.cachewrapper.piece.data.Location;
import org.cachewrapper.piece.type.GamePiece;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@RequiredArgsConstructor
public class FenGameBoardParser implements GameBoardParser {

    private final PieceFactoryFacade pieceFactoryFacade;

    @Override
    public @NotNull Map<Location, GamePiece> parseGameBoard(@NotNull UUID whitePlayerUUID, @NotNull UUID blackPlayerUUID) {
        final Map<Location, GamePiece> gamePieces = new HashMap<>();

        final String piecesPositions = parsingString().split(" ")[0];
        final String[] piecesArray = piecesPositions.split("/");

        int coordinateY = 0;
        for (String pieces : piecesArray) {
            int coordinateX = 0;
            final char[] piecesSymbols = pieces.toCharArray();

            for (char character : piecesSymbols) {
                if (Character.isDigit(character)) {
                    coordinateX = coordinateX + character - '0';
                    continue;
                }

                final UUID pieceOwnerUUID = Character.isUpperCase(character) ? whitePlayerUUID : blackPlayerUUID;
                final GamePiece gamePiece = pieceFactoryFacade.createGamePiece(character, pieceOwnerUUID);
                if (gamePiece == null) {
                    continue;
                }

                gamePieces.put(new Location(coordinateX, coordinateY), gamePiece);
                coordinateX++;
            }

            coordinateY++;
        }

        return gamePieces;
    }

    @Override
    public @NotNull String parsingString() {
        return "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    }
}
