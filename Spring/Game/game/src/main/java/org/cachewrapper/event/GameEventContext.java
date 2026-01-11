package org.cachewrapper.event;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.Game;
import org.cachewrapper.board.GameBoardContext;
import org.cachewrapper.event.listener.GameEventListener;
import org.cachewrapper.event.type.Event;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class GameEventContext {

    private final Map<Class<? extends Event>, GameEventListener<?>> listeners = new ConcurrentHashMap<>();

    public <E extends Event> void register(@NotNull GameEventListenerData<E> listenerData) {
        final Class<E> eventType = listenerData.eventType();
        final GameEventListener<E> eventListener = new GameEventListener<>(listenerData);

        listeners.put(eventType, eventListener);
    }

    public void unregister(@NotNull Class<? extends Event> eventType) {
        listeners.remove(eventType);
    }

    public void unregisterAll() {
        listeners.clear();
    }

    public void callEvent(@NotNull Event event) {
        final GameEventListener<?> eventListener = listeners.get(event.getClass());
        if (eventListener == null) {
            return;
        }

        eventListener.execute(event);
    }
}