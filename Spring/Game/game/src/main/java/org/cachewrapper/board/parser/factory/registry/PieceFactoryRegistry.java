package org.cachewrapper.board.parser.factory.registry;

import org.cachewrapper.board.parser.factory.PieceFactory;
import org.cachewrapper.board.parser.factory.impl.PawnPieceFactory;
import org.cachewrapper.piece.type.GamePiece;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PieceFactoryRegistry {

    private final Map<String, PieceFactory<?>> pieceFactoryMap = new HashMap<>();

    public PieceFactoryRegistry() {
        registerDefaults();
    }

    @SuppressWarnings("unchecked")
    public <G extends GamePiece, P extends PieceFactory<G>> P getPieceFactory(@NotNull String identifier) {
        return (P) pieceFactoryMap.get(identifier);
    }

    private void registerDefaults() {
        Map.of(
                "p", new PawnPieceFactory()
        ).forEach(this::registerFactory);
    }

    private void registerFactory(@NotNull String identifier, @NotNull PieceFactory<?> pieceFactory) {
        pieceFactoryMap.put(identifier, pieceFactory);
    }
}