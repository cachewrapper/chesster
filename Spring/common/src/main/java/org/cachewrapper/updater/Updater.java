package org.cachewrapper.updater;

import org.cachewrapper.event.Event;

public interface Updater<E extends Event> {
    void update(E event);
}