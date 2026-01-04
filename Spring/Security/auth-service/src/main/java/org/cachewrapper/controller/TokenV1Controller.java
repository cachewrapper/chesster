package org.cachewrapper.controller;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.service.impl.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class TokenV1Controller {

    private final TokenService tokenService;

    @PostMapping("/token/refresh")
    public ResponseEntity<String> refreshToken(@CookieValue(value = "refresh_token") String refreshTokenString) {
        return tokenService.refreshToken(refreshTokenString);
    }

    @GetMapping("/token/validate")
    public ResponseEntity<String> validateToken(@CookieValue(value = "access_token") String accessTokenString) {
        return tokenService.validateAccessToken(accessTokenString);
    }
}