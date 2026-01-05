package org.cachewrapper.command.service;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.domain.LogoutUserCommand;
import org.cachewrapper.exception.AccessTokenInvalidException;
import org.cachewrapper.token.domain.token.AccessToken;
import org.cachewrapper.token.repository.RefreshTokenRepository;
import org.cachewrapper.token.service.invalid.InvalidAccessTokenService;
import org.cachewrapper.token.service.token.AccessTokenService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutUserCommandService implements CommandService<Void, LogoutUserCommand> {

    private final AccessTokenService accessTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final InvalidAccessTokenService invalidAccessTokenService;

    @Override
    public Void execute(LogoutUserCommand command) {
        final String accessTokenString = command.accessTokenString();
        if (!accessTokenService.validateTokenString(accessTokenString)) {
            throw new AccessTokenInvalidException();
        }

        final AccessToken accessToken = accessTokenService.getTokenFromString(accessTokenString);
        final String refreshTokenString = command.refreshTokenString();

        refreshTokenRepository.deleteByUserUUIDAndRefreshTokenString(accessToken.userUUID(), refreshTokenString);
        invalidAccessTokenService.invalidateToken(accessTokenString, accessTokenService);
        return null;
    }
}