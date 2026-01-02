package org.cachewrapper.command.handler;

import org.cachewrapper.command.domain.Command;
import org.jetbrains.annotations.NotNull;

public interface CommandHandler<T extends Command> {
    void handleCommand(@NotNull T command);
}