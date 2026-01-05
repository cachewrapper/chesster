package org.cachewrapper.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.cachewrapper.service.impl.TokenService;
import org.cachewrapper.token.filter.domain.SessionAuthentication;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class TokenV1Controller {

    private final TokenService tokenService;

    @PostMapping("/token/refresh")
    public ResponseEntity<String> refreshToken(@CookieValue(value = "refresh_token_uuid") String refreshTokenUUIDString) {
        var refreshTokenUUID = UUID.fromString(refreshTokenUUIDString);
        return tokenService.refreshToken(refreshTokenUUID);
    }

    @GetMapping("/token/validate")
    public ResponseEntity<String> validateToken(@AuthenticationPrincipal @Nullable SessionAuthentication authentication) {
        System.out.println("Validation...");
        return tokenService.validateAccessToken(authentication);
    }
}