package org.cachewrapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.domain.LoginUserCommand;
import org.cachewrapper.command.domain.LogoutUserCommand;
import org.cachewrapper.command.domain.RegisterUserCommand;
import org.cachewrapper.command.response.JsonWebTokenResponse;
import org.cachewrapper.command.service.LoginUserCommandService;
import org.cachewrapper.command.service.LogoutUserCommandService;
import org.cachewrapper.command.service.RegisterUserCommandService;
import org.cachewrapper.exception.*;
import org.cachewrapper.service.AuthService;
import org.cachewrapper.token.filter.domain.SessionAuthentication;
import org.cachewrapper.token.service.token.AccessTokenService;
import org.cachewrapper.token.service.token.RefreshTokenService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthService {

    private final RegisterUserCommandService registerUserCommandService;
    private final LoginUserCommandService loginUserCommandService;
    private final LogoutUserCommandService logoutUserCommandService;

    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    @NotNull
    public ResponseEntity<String> register(@NotNull String email, @NotNull String username, @NotNull String password) {
        try {
            final RegisterUserCommand registerUserCommand = new RegisterUserCommand(email, username, password);
            final JsonWebTokenResponse jsonWebTokenResponse = registerUserCommandService.execute(registerUserCommand);

            final String accessTokenString = jsonWebTokenResponse.accessTokenString();
            final String refreshTokenUUIDString = jsonWebTokenResponse.refreshTokenUUIDString();

            return generateTokensResponse(accessTokenService, refreshTokenService, accessTokenString, refreshTokenUUIDString);
        } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @NotNull
    public ResponseEntity<String> login(@NotNull String email, @NotNull String password) {
        try {
            final LoginUserCommand loginUserCommand = new LoginUserCommand(email, password);
            final JsonWebTokenResponse jsonWebTokenResponse = loginUserCommandService.execute(loginUserCommand);

            final String accessTokenString = jsonWebTokenResponse.accessTokenString();
            final String refreshTokenString = jsonWebTokenResponse.refreshTokenUUIDString();

            return generateTokensResponse(accessTokenService, refreshTokenService, accessTokenString, refreshTokenString);
        } catch (EmailNotFoundException | PasswordDoesNotMatchException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @NotNull
    public ResponseEntity<String> logout(@Nullable SessionAuthentication authentication) {
        var accessTokenCookie = generateCookie("access_token", "", Duration.ZERO);
        var refreshTokenUUIDCookie = generateCookie("refresh_token_uuid", "", Duration.ZERO);

        try {
            if (authentication == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            final String accessTokenString = authentication.getAccessTokenString();
            final String refreshTokenString = authentication.getRefreshTokenString();

            final LogoutUserCommand logoutUserCommand = new LogoutUserCommand(accessTokenString, refreshTokenString);
            logoutUserCommandService.execute(logoutUserCommand);

            return ResponseEntity.ok()
                    .header("Set-Cookie", accessTokenCookie.toString())
                    .header("Set-Cookie", refreshTokenUUIDCookie.toString())
                    .build();
        } catch (AccessTokenInvalidException exception) {
            return ResponseEntity.ok()
                    .header("Set-Cookie", accessTokenCookie.toString())
                    .header("Set-Cookie", refreshTokenUUIDCookie.toString())
                    .build();
        }
    }
}