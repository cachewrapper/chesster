package org.cachewrapper.command.service;

import org.cachewrapper.command.domain.Command;

public interface CommandService<T, C extends Command> {
    T execute(C command);
}