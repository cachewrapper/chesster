package org.cachewrapper.controller;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.aggregate.UserAggregate;
import org.cachewrapper.query.domain.UserViewDto;
import org.cachewrapper.query.service.UserQueryService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserV1Controller {

    private final UserQueryService userQueryService;

    @GetMapping("/me")
    //TODO: Make custom authentication domain object
    public UserViewDto me(Authentication authentication) {
        var userUUID = UUID.fromString((String) authentication.getPrincipal());
        return userQueryService.findById(userUUID);
    }
}