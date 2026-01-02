package org.cachewrapper.command.coordinator;

import org.cachewrapper.command.domain.Command;

public interface CommandCoordinator<T extends Command> {
    void coordinate(T command);
}