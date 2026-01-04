package org.cachewrapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.coordinator.AccountCreateCommandCoordinator;
import org.cachewrapper.command.coordinator.AddRefreshTokenCommandCoordinator;
import org.cachewrapper.command.coordinator.RemoveRefreshTokenCommandCoordinator;
import org.cachewrapper.command.domain.AccountCreateCommand;
import org.cachewrapper.command.domain.AddRefreshTokenCommand;
import org.cachewrapper.command.domain.RemoveRefreshTokenCommand;
import org.cachewrapper.exception.EmailAlreadyExistsException;
import org.cachewrapper.exception.UsernameAlreadyExistsException;
import org.cachewrapper.query.service.UserCredentialsQueryService;
import org.cachewrapper.service.AuthService;
import org.cachewrapper.token.domain.payload.AccessTokenPayload;
import org.cachewrapper.token.domain.payload.RefreshTokenPayload;
import org.cachewrapper.token.service.invalid.InvalidAccessTokenService;
import org.cachewrapper.token.service.token.AccessTokenService;
import org.cachewrapper.token.service.token.RefreshTokenService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthService {

    private final AccountCreateCommandCoordinator accountCreateCommandCoordinator;
    private final AddRefreshTokenCommandCoordinator updateRefreshTokenCommandCoordinator;
    private final RemoveRefreshTokenCommandCoordinator removeRefreshTokenCommandCoordinator;
    private final UserCredentialsQueryService userCredentialsQueryService;
    private final InvalidAccessTokenService invalidAccessTokenService;

    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    @NotNull
    public ResponseEntity<String> register(
            @NotNull String email,
            @NotNull String username,
            @NotNull String password,
            @Nullable String accessTokenCookieString
    ) {
        if (accessTokenCookieString != null && accessTokenService.validateTokenString(accessTokenCookieString)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

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
            return ResponseEntity.badRequest().body("Username or email already exists!");
        }
    }

    @NotNull
    public ResponseEntity<String> login(
            @NotNull String email,
            @NotNull String password,
            @Nullable String accessTokenCookieString
    ) {
        if (accessTokenCookieString != null && accessTokenService.validateTokenString(accessTokenCookieString)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

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

        var updateRefreshTokenCommand = new AddRefreshTokenCommand(userUUID, refreshTokenString);
        updateRefreshTokenCommandCoordinator.coordinate(updateRefreshTokenCommand);

        return generateTokensResponse(accessTokenService, refreshTokenService, accessTokenString, refreshTokenString);
    }

    @NotNull
    public ResponseEntity<String> logout(@NotNull String accessTokenString, @NotNull String refreshTokenString) {
        if (accessTokenString.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var accessToken = accessTokenService.getTokenFromString(accessTokenString);
        var userUUID = accessToken.userUUID();

        var removeRefreshTokenCommand = new RemoveRefreshTokenCommand(userUUID, refreshTokenString);
        removeRefreshTokenCommandCoordinator.coordinate(removeRefreshTokenCommand);

        var accessTokenCookie = generateCookie("access_token", "", Duration.ZERO);
        var refreshTokenCookie = generateCookie("refresh_token", "", Duration.ZERO);

        invalidAccessTokenService.invalidateToken(accessTokenString, accessTokenService);
        return ResponseEntity.ok()
                .header("Set-Cookie", accessTokenCookie.toString())
                .header("Set-Cookie", refreshTokenCookie.toString())
                .build();
    }
}