package org.cachewrapper.mechanic;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.Game;
import org.cachewrapper.mechanic.type.GameMechanic;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class GameMechanicContext {

    private final Map<Class<? extends GameMechanic<?>>, GameMechanic<?>> gameMechanics = new ConcurrentHashMap<>();

    private final Game game;

    @SuppressWarnings("unchecked")
    public void enable(@NotNull GameMechanic<?> gameMechanic) {
        final Class<? extends GameMechanic<?>> mechanicType = (Class<? extends GameMechanic<?>>) gameMechanic.getClass();

        gameMechanic.enable();
        gameMechanics.put(mechanicType, gameMechanic);
    }

    public void disable(@NotNull Class<? extends GameMechanic<?>> mechanicType) {
        final GameMechanic<?> mechanic = gameMechanics.remove(mechanicType);
        mechanic.disable(game);
    }
}