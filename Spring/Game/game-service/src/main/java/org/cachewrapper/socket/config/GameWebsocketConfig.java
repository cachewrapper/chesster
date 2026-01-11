package org.cachewrapper.socket.config;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.interceptor.JwtHandshakeInterceptor;
import org.cachewrapper.token.service.token.AccessTokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class GameWebsocketConfig implements WebSocketMessageBrokerConfigurer {

    private final AccessTokenService accessTokenService;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-game")
                .setAllowedOriginPatterns("*")
                .addInterceptors(new JwtHandshakeInterceptor(accessTokenService))
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
