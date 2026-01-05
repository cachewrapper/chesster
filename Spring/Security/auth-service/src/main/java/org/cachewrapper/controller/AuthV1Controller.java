package org.cachewrapper.controller;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.controller.request.AccountLoginRequest;
import org.cachewrapper.controller.request.AccountRegisterRequest;
import org.cachewrapper.service.impl.AuthenticationService;
import org.cachewrapper.token.filter.domain.SessionAuthentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthV1Controller {

    private final AuthenticationService authenticationService;

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

    @PostMapping("/logout")
    public ResponseEntity<String> logout(SessionAuthentication authentication) {
        return authenticationService.logout(authentication);
    }
}