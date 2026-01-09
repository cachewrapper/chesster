package org.cachewrapper.board.tracker;

import org.cachewrapper.piece.data.Location;
import org.cachewrapper.piece.game.type.GamePiece;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameBoardPieceTracker {

    private final Map<Location, GamePiece> pieces = new ConcurrentHashMap<>();

    public void addPieceLocation(@NotNull Location location, @NotNull GamePiece gamePiece) {
        pieces.put(location, gamePiece);
    }

    public GamePiece getPiece(@NotNull Location location) {
        return pieces.get(location);
    }

    @NotNull
    public GamePiece removePieceLocation(@NotNull Location location) {
        return pieces.remove(location);
    }

    @NotNull
    @UnmodifiableView
    public Map<Location, GamePiece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }
}
