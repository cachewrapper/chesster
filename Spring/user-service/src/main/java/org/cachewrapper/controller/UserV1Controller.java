package org.cachewrapper.controller;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.aggregate.UserAggregate;
import org.cachewrapper.data.domain.UserEntity;
import org.cachewrapper.data.service.UserService;
import org.cachewrapper.exception.avatar.AvatarNullException;
import org.cachewrapper.exception.avatar.AvatarSizeReachedException;
import org.cachewrapper.token.filter.domain.SessionAuthentication;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserV1Controller {

    private final UserService userService;

    @GetMapping("/profile")
    public UserAggregate profile(@AuthenticationPrincipal @Nullable SessionAuthentication authentication) {
        if (authentication == null) {
            return null;
        }

        var userUUID = authentication.getUserUUID();
        return userService.findById(userUUID);
    }

    @PutMapping("/upload/avatar")
    public ResponseEntity<String> uploadProfileAvatar(
            @AuthenticationPrincipal @Nullable SessionAuthentication authentication,
            @RequestParam("file") MultipartFile avatarFile
    ) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            userService.uploadProfileAvatar(authentication, avatarFile);
            return ResponseEntity.ok().build();
        } catch (AvatarSizeReachedException | AvatarNullException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}