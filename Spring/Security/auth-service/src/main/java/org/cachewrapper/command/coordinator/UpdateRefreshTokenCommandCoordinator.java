package org.cachewrapper.command.coordinator;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.domain.UpdateRefreshTokenCommand;
import org.cachewrapper.command.handler.UpdateRefreshTokenCommandHandler;
import org.cachewrapper.exception.RefreshTokenInvalidException;
import org.cachewrapper.token.service.RefreshTokenService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateRefreshTokenCommandCoordinator implements CommandCoordinator<UpdateRefreshTokenCommand> {

    private final UpdateRefreshTokenCommandHandler updateRefreshTokenCommandHandler;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void coordinate(UpdateRefreshTokenCommand command) {
        var refreshTokenString = command.refreshTokenString();
        boolean isTokenValid = refreshTokenService.validateTokenString(refreshTokenString);

        if (!isTokenValid) {
            throw new RefreshTokenInvalidException();
        }

        updateRefreshTokenCommandHandler.handleCommand(command);
    }
}