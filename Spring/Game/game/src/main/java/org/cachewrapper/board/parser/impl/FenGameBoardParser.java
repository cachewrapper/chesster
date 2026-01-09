package org.cachewrapper.board.parser.impl;

import org.cachewrapper.board.parser.GameBoardParser;
import org.cachewrapper.piece.game.type.GamePiece;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class FenGameBoardParser implements GameBoardParser {

    @Override
    public @NotNull Collection<GamePiece> parseGameBoard(@NotNull UUID whitePlayerUUID, @NotNull UUID blackPlayerUUID) {
        final Collection<GamePiece> gamePieces = new ArrayList<>();

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
                final GamePiece gamePiece =
            }
        }
    }

    @Override
    public @NotNull String parsingString() {
        return "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    }

    @NotNull
    private GamePiece loadPiece(char character) {

    }
}
