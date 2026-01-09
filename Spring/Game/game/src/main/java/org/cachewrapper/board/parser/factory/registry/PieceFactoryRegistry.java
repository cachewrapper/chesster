package org.cachewrapper.board.parser.factory.registry;

import org.cachewrapper.board.parser.factory.PieceFactory;

import java.util.HashMap;
import java.util.Map;

public class PieceFactoryRegistry {

    private final Map<String, PieceFactory<?>> pieceFactoryMap = new HashMap<>();

    public PieceFactoryRegistry() {

    }

    private void registerDefaults() {
        Map.of(
                "p",
        )
    }
}