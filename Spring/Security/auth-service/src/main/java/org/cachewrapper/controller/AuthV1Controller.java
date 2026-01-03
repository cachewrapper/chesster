package org.cachewrapper.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.coordinator.AccountCreateCommandCoordinator;
import org.cachewrapper.command.domain.AccountCreateCommand;
import org.cachewrapper.controller.request.AccountLoginRequest;
import org.cachewrapper.controller.request.AccountRegisterRequest;
import org.cachewrapper.exception.EmailAlreadyExistsException;
import org.cachewrapper.exception.UsernameAlreadyExistsException;
import org.cachewrapper.query.service.UserCredentialsQueryService;
import org.cachewrapper.service.AuthenticationService;
import org.cachewrapper.token.domain.payload.AccessTokenPayload;
import org.cachewrapper.token.service.impl.AccessTokenService;
import org.jetbrains.annotations.NotNull;
import org.postgresql.util.PSQLException;
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

    private final AuthenticationService authenticationService;

    @RequestMapping("/**")
    public String a(HttpServletRequest request) {
        return request.getRequestURI();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AccountRegisterRequest accountRegisterRequest) {
        var email = accountRegisterRequest.email();
        var username = accountRegisterRequest.username();
        var password = accountRegisterRequest.password();

        return authenticationService.register(email, username, password);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AccountLoginRequest accountLoginRequest) {
        var email = accountLoginRequest.email();
        var password = accountLoginRequest.password();

        return authenticationService.login(email, password);
    }
}