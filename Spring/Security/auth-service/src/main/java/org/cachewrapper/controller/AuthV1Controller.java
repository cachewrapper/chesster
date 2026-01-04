package org.cachewrapper.controller;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.controller.request.AccountLoginRequest;
import org.cachewrapper.controller.request.AccountRegisterRequest;
import org.cachewrapper.service.impl.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthV1Controller {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody AccountRegisterRequest accountRegisterRequest,
            @CookieValue(value = "access_token", required = false) String accessTokenString
    ) {
        var email = accountRegisterRequest.email();
        var username = accountRegisterRequest.username();
        var password = accountRegisterRequest.password();

        return authenticationService.register(email, username, password, accessTokenString);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody AccountLoginRequest accountLoginRequest,
            @CookieValue(value = "access_token", required = false) String accessTokenString
    ) {
        var email = accountLoginRequest.email();
        var password = accountLoginRequest.password();

        return authenticationService.login(email, password, accessTokenString);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @CookieValue(value = "access_token") String accessTokenString,
            @CookieValue(value = "refresh_token_uuid") String refreshTokenUUIDString
    ) {
        return authenticationService.logout(accessTokenString, refreshTokenUUIDString);
    }
}