package org.cachewrapper.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.cachewrapper.token.service.token.AccessTokenService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final AccessTokenService accessTokenService;

    @Override
    @SneakyThrows
    public boolean beforeHandshake(
            ServerHttpRequest serverHttpRequest,
            ServerHttpResponse serverHttpResponse,
            WebSocketHandler webSocketHandler,
            Map<String, Object> attributes
    ) {
        final String accessToken = getCookieValue(serverHttpRequest, "access_token");
        if (accessToken == null || !accessTokenService.validateTokenString(accessToken)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }

        return true;
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception
    ) {}

    @Nullable
    private String getCookieValue(
            final @NotNull ServerHttpRequest serverHttpRequest,
            final @NotNull String cookieName
    ) {
        final List<String> cookieHeaders = serverHttpRequest.getHeaders().get("Cookie");
        if (cookieHeaders == null) {
            return null;
        }

        return cookieHeaders.stream()
                .flatMap(header -> Arrays.stream(header.split(";")))
                .map(String::trim)
                .map(cookie -> cookie.split("=", 2))
                .filter(pair -> pair.length == 2 && pair[0].equals(cookieName))
                .map(pair -> pair[1])
                .findFirst()
                .orElse(null);
    }
}