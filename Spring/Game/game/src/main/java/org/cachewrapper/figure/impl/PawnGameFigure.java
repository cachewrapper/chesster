package org.cachewrapper.figure.impl;

import org.cachewrapper.figure.GameFigure;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import java.util.UUID;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PawnGameFigure extends GameFigure {

    public PawnGameFigure(@NotNull UUID playerUUID, int column, int row) {
        super(playerUUID, column, row);
    }

    @Override
    public String identifier() {
        return "pawn";
    }
}