package org.cachewrapper.board.parser;

import org.cachewrapper.piece.data.Location;
import org.cachewrapper.piece.type.GamePiece;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public interface GameBoardParser {

    @NotNull
    Map<Location, GamePiece> parseGameBoard(@NotNull UUID whitePlayerUUID, @NotNull UUID blackPlayerUUID);

    @NotNull
    String parsingString();
}