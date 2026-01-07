package org.cachewrapper.figure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public abstract class GameFigure {

    private final UUID playerUUID;

    public abstract String identifier();
}