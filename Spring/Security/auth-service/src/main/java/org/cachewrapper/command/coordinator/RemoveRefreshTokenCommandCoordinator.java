package org.cachewrapper.command.coordinator;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.domain.RemoveRefreshTokenCommand;
import org.cachewrapper.command.handler.AddRefreshTokenCommandHandler;
import org.cachewrapper.command.handler.RemoveRefreshTokenCommandHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveRefreshTokenCommandCoordinator implements CommandCoordinator<RemoveRefreshTokenCommand> {

    private final RemoveRefreshTokenCommandHandler removeRefreshTokenCommandHandler;

    @Override
    public void coordinate(RemoveRefreshTokenCommand command) {
        removeRefreshTokenCommandHandler.handleCommand(command);
    }
}