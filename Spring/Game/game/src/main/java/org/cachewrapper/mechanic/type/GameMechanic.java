package org.cachewrapper.mechanic.type;

import org.cachewrapper.Game;
import org.cachewrapper.event.GameEventContext;
import org.cachewrapper.event.type.Event;
import org.jetbrains.annotations.NotNull;

public interface GameMechanic<E extends Event> {

    default void disable(@NotNull Game game) {
        final GameEventContext eventContext = game.getEventContext();
        final Class<E> eventType = getEventType();

        eventContext.unregister(eventType);
    }

    void enable();

    Class<E> getEventType();
}
