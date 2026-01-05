package org.cachewrapper.command.service;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.aggregate.UserCredentialsAggregate;
import org.cachewrapper.command.domain.LoginUserCommand;
import org.cachewrapper.command.response.JsonWebTokenResponse;
import org.cachewrapper.data.service.UserCredentialsService;
import org.cachewrapper.exception.EmailNotFoundException;
import org.cachewrapper.exception.PasswordDoesNotMatchException;
import org.cachewrapper.token.domain.payload.AccessTokenPayload;
import org.cachewrapper.token.domain.payload.RefreshTokenPayload;
import org.cachewrapper.token.repository.RefreshTokenRepository;
import org.cachewrapper.token.repository.domain.RefreshTokenEntity;
import org.cachewrapper.token.service.token.AccessTokenService;
import org.cachewrapper.token.service.token.RefreshTokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginUserCommandService implements CommandService<JsonWebTokenResponse, LoginUserCommand> {

    private final UserCredentialsService userCredentialsService;
    private final BCryptPasswordEncoder passwordEncoder;

    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public JsonWebTokenResponse execute(LoginUserCommand command) {
        final String email = command.email();
        final String password = command.password();

        final UserCredentialsAggregate userCredentialsAggregate = userCredentialsService.findByEmail(email);
        if (userCredentialsAggregate == null) {
            throw new EmailNotFoundException();
        }

        if (!passwordEncoder.matches(password, userCredentialsAggregate.getPasswordHash())) {
            throw new PasswordDoesNotMatchException();
        }

        final UUID userUUID = userCredentialsAggregate.getUserUUID();
        final String username = userCredentialsAggregate.getUsername();

        return createResponse(userUUID, username);
    }

    @NotNull
    private JsonWebTokenResponse createResponse(@NotNull UUID userUUID, @NotNull String username) {
        final AccessTokenPayload accessTokenPayload = new AccessTokenPayload(userUUID, username);
        final String accessTokenString = accessTokenService.generateTokenString(accessTokenPayload);

        final RefreshTokenPayload refreshTokenPayload = new RefreshTokenPayload(userUUID, accessTokenString);
        final String refreshTokenString = refreshTokenService.generateTokenString(refreshTokenPayload);

        final RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(UUID.randomUUID(), userUUID, refreshTokenString);
        refreshTokenRepository.save(refreshTokenEntity);

        return new JsonWebTokenResponse(accessTokenString, refreshTokenEntity.getRefreshTokenUUID().toString());
    }
}