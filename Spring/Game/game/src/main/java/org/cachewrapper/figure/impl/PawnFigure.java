package org.cachewrapper.figure.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.figure.GameFigure;

import java.util.UUID;

public class PawnFigure extends GameFigure {

    public PawnFigure(UUID playerUUID) {
        super(playerUUID);
    }

    @Override
    public String identifier() {
        return "pawn";
    }
}