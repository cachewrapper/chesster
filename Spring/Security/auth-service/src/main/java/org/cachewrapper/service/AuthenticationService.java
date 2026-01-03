package org.cachewrapper.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.cachewrapper.command.coordinator.AccountCreateCommandCoordinator;
import org.cachewrapper.command.domain.AccountCreateCommand;
import org.cachewrapper.exception.EmailAlreadyExistsException;
import org.cachewrapper.exception.UsernameAlreadyExistsException;
import org.cachewrapper.query.service.UserCredentialsQueryService;
import org.cachewrapper.token.domain.payload.AccessTokenPayload;
import org.cachewrapper.token.service.impl.AccessTokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AccountCreateCommandCoordinator accountCreateCommandCoordinator;
    private final UserCredentialsQueryService userCredentialsQueryService;

    private final AccessTokenService accessTokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    @NotNull
    public ResponseEntity<String> register(
            @NotNull String email,
            @NotNull String username,
            @NotNull String password
    ) {
        try {
            var userUUID = UUID.randomUUID();
            var accountCreateCommand = new AccountCreateCommand(userUUID, email, username, password);
            accountCreateCommandCoordinator.coordinate(accountCreateCommand);

            var accessTokenPayload = new AccessTokenPayload(userUUID, username);
            var accessTokenString = accessTokenService.generateTokenString(accessTokenPayload);

            var expirationDuration = accessTokenService.getExpirationDuration();
            var accessTokenCookie = generateCookie("access_token", accessTokenString, expirationDuration);

            return ResponseEntity.ok()
                    .header("Set-Cookie", accessTokenCookie.toString())
                    .build();
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

        var accessTokenPayload = new AccessTokenPayload(userCredentialsView.getUserUUID(), userCredentialsView.getUsername());
        var accessTokenString = accessTokenService.generateTokenString(accessTokenPayload);

        var expirationDuration = accessTokenService.getExpirationDuration();
        var accessTokenCookie = generateCookie("access_token", accessTokenString, expirationDuration);

        return ResponseEntity.ok()
                .header("Set-Cookie", accessTokenCookie.toString())
                .build();
    }

    @NotNull
    private ResponseCookie generateCookie(
            @NotNull String identifier,
            @NotNull String value,
            @NotNull Duration expirationDuration
    ) {
        return ResponseCookie.from(identifier, value)
                .httpOnly(false)
                .secure(false)
                .path("/")
                .maxAge(expirationDuration)
                .sameSite("Lax")
                .build();
    }
}