package org.cachewrapper.event.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.event.Event;

import java.util.UUID;

@RequiredArgsConstructor
@Data
public class AccountCreateEvent implements Event {
    private final UUID userUUID;
    private final String email;
    private final String username;
}