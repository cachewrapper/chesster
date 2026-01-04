package org.cachewrapper.command.coordinator;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.domain.AddRefreshTokenCommand;
import org.cachewrapper.command.handler.AddRefreshTokenCommandHandler;
import org.cachewrapper.exception.RefreshTokenInvalidException;
import org.cachewrapper.token.service.token.RefreshTokenService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddRefreshTokenCommandCoordinator implements CommandCoordinator<AddRefreshTokenCommand> {

    private final AddRefreshTokenCommandHandler updateRefreshTokenCommandHandler;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void coordinate(AddRefreshTokenCommand command) {
        var refreshTokenString = command.refreshTokenString();
        boolean isTokenValid = refreshTokenService.validateTokenString(refreshTokenString);

        if (!isTokenValid) {
            throw new RefreshTokenInvalidException();
        }

        updateRefreshTokenCommandHandler.handleCommand(command);
    }
}