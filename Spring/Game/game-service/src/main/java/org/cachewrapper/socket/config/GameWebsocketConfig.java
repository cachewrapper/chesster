package org.cachewrapper.socket.config;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.interceptor.JwtHandshakeInterceptor;
import org.cachewrapper.socket.handler.GameConnectionHandler;
import org.cachewrapper.token.service.token.AccessTokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class GameWebsocketConfig implements WebSocketConfigurer {

    private final AccessTokenService accessTokenService;
    private final GameConnectionHandler gameConnectionHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(gameConnectionHandler, "/ws")
                .addInterceptors(new JwtHandshakeInterceptor(accessTokenService))
                .setAllowedOrigins("*");
    }
}
