package org.cachewrapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.coordinator.AccountCreateCommandCoordinator;
import org.cachewrapper.command.coordinator.UpdateRefreshTokenCommandCoordinator;
import org.cachewrapper.command.domain.AccountCreateCommand;
import org.cachewrapper.command.domain.UpdateRefreshTokenCommand;
import org.cachewrapper.exception.EmailAlreadyExistsException;
import org.cachewrapper.exception.UsernameAlreadyExistsException;
import org.cachewrapper.query.service.UserCredentialsQueryService;
import org.cachewrapper.service.AuthService;
import org.cachewrapper.token.domain.payload.AccessTokenPayload;
import org.cachewrapper.token.domain.payload.RefreshTokenPayload;
import org.cachewrapper.token.service.AccessTokenService;
import org.cachewrapper.token.service.RefreshTokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthService {

    private final AccountCreateCommandCoordinator accountCreateCommandCoordinator;
    private final UpdateRefreshTokenCommandCoordinator updateRefreshTokenCommandCoordinator;
    private final UserCredentialsQueryService userCredentialsQueryService;

    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    @NotNull
    public ResponseEntity<String> register(
            @NotNull String email,
            @NotNull String username,
            @NotNull String password
    ) {
        try {
            var userUUID = UUID.randomUUID();
            var accessTokenPayload = new AccessTokenPayload(userUUID, username);
            var refreshTokenPayload = new RefreshTokenPayload(userUUID, username);

            var accessTokenString = accessTokenService.generateTokenString(accessTokenPayload);
            var refreshTokenString = refreshTokenService.generateTokenString(refreshTokenPayload);

            var accountCreateCommand = new AccountCreateCommand(userUUID, email, username, password, refreshTokenString);
            accountCreateCommandCoordinator.coordinate(accountCreateCommand);

            return generateTokensResponse(accessTokenService, refreshTokenService, accessTokenString, refreshTokenString);
        } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException exception) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @NotNull
    public ResponseEntity<String> login(
            @NotNull String email,
            @NotNull String password
    ) {
        var userCredentialsView = userCredentialsQueryService.findByEmail(email);
        if (userCredentialsView == null) {
            return ResponseEntity.notFound().build();
        }

        var passwordHash = userCredentialsView.getPasswordHash();
        var isPasswordCorrect = passwordEncoder.matches(password, passwordHash);
        if (!isPasswordCorrect) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }

        var userUUID = userCredentialsView.getUserUUID();
        var accessTokenPayload = new AccessTokenPayload(userUUID, userCredentialsView.getUsername());
        var accessTokenString = accessTokenService.generateTokenString(accessTokenPayload);

        var refreshTokenPayload = new RefreshTokenPayload(userCredentialsView.getUserUUID(), userCredentialsView.getUsername());
        var refreshTokenString = refreshTokenService.generateTokenString(refreshTokenPayload);

        var updateRefreshTokenCommand = new UpdateRefreshTokenCommand(userUUID, refreshTokenString);
        updateRefreshTokenCommandCoordinator.coordinate(updateRefreshTokenCommand);

        return generateTokensResponse(accessTokenService, refreshTokenService, accessTokenString, refreshTokenString);
    }
}