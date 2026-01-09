package org.cachewrapper.board.parser;

import org.cachewrapper.piece.game.type.GamePiece;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.UUID;

public interface GameBoardParser {

    @NotNull
    Collection<GamePiece> parseGameBoard(@NotNull UUID whitePlayerUUID, @NotNull UUID blackPlayerUUID);

    @NotNull
    String parsingString();
}