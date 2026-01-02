package org.cachewrapper.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.coordinator.AccountCreateCommandCoordinator;
import org.cachewrapper.command.domain.AccountCreateCommand;
import org.cachewrapper.controller.request.AccountRegisterRequest;
import org.cachewrapper.exception.EmailAlreadyExistsException;
import org.cachewrapper.exception.UsernameAlreadyExistsException;
import org.cachewrapper.token.domain.payload.AccessTokenPayload;
import org.cachewrapper.token.service.impl.AccessTokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthV1Controller {

    private final AccountCreateCommandCoordinator accountCreateCommandCoordinator;
    private final AccessTokenService accessTokenService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody AccountRegisterRequest accountRegisterRequest,
            HttpServletResponse httpServletResponse
    ) {
        var email = accountRegisterRequest.email();
        var username = accountRegisterRequest.username();
        var password = accountRegisterRequest.password();

        try {
            var userUUID = UUID.randomUUID();
            var accountCreateCommand = new AccountCreateCommand(userUUID, email, username, password);
            accountCreateCommandCoordinator.coordinate(accountCreateCommand);

            var accessTokenPayload = new AccessTokenPayload(userUUID, username);
            var accessTokenString = accessTokenService.generateTokenString(accessTokenPayload);

            var expirationDuration = accessTokenService.getExpirationDuration();
            var accessTokenCookie = generateCookie("access_token", accessTokenString, expirationDuration);

            httpServletResponse.addHeader("Set-Cookie", accessTokenCookie.toString());
            return ResponseEntity.ok().body(accessTokenString);
        } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException exception) {
            return ResponseEntity.badRequest().body("Error");
        }
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