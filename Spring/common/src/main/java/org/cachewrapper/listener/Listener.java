package org.cachewrapper.listener;

import org.cachewrapper.event.Event;

public interface Listener<E extends Event> {
    void listener(E event);
}