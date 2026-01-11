package org.cachewrapper.event.listener;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.Game;
import org.cachewrapper.event.GameEventListenerData;
import org.cachewrapper.event.type.Event;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class GameEventListener<E extends Event> {

    private final GameEventListenerData<E> listenerData;

    public void execute(@NotNull Event event) {
        final Class<E> eventType = listenerData.eventType();
        if (event.getClass() != eventType) {
            return;
        }

        E castedEvent = eventType.cast(event);
        final Consumer<E> eventAction = listenerData.eventAction();
        eventAction.accept(castedEvent);
    }
}