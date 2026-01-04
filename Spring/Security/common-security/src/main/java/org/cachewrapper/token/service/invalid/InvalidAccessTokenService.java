package org.cachewrapper.token.service.invalid;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.token.service.token.AccessTokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvalidAccessTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    public void invalidateToken(@NotNull String accessTokenString, @NotNull AccessTokenService accessTokenService) {
        if (accessTokenString.isEmpty()) {
            return;
        }

        if (!accessTokenService.validateTokenString(accessTokenString)) {
            return;
        }

        var expirationDuration = accessTokenService.getExpirationDuration();
        redisTemplate.opsForValue().set(accessTokenString, "", expirationDuration);
    }

    public boolean validateAccessToken(@NotNull String accessTokenString) {
        if (accessTokenString.isEmpty()) {
            return false;
        }

        return !redisTemplate.hasKey(accessTokenString);
    }
}