package org.cachewrapper.event.type;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class GameJoinEvent implements Event {
    private final UUID playerUUID;
}