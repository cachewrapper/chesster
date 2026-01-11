package org.cachewrapper.event;

import lombok.Builder;

import java.util.function.Consumer;

@Builder
public record GameEventListenerData<E>(
        Class<E> eventType,
        Consumer<E> eventAction
) {}