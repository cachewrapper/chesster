package org.cachewrapper.socket.controller;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.Game;
import org.cachewrapper.RankedGame;
import org.cachewrapper.event.type.GameJoinEvent;
import org.cachewrapper.token.service.token.AccessTokenService;
import org.cachewrapper.tracker.GameTracker;
import org.jspecify.annotations.Nullable;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class GameWebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GameTracker gameTracker;
    private final AccessTokenService accessTokenService;

    @MessageMapping("/join/{gameUUID}")
    public void join(
            @DestinationVariable UUID gameUUID,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        @Nullable Map<String, Object> attributes = headerAccessor.getSessionAttributes();
        if (attributes == null) {
            return;
        }

        final UUID userUUID = (UUID) attributes.get("userUUID");
        Game game = gameTracker.getGame(gameUUID);
        if (game == null) {
            game = new RankedGame();
            game.start();
        }

        game.getEventContext().callEvent(new GameJoinEvent(userUUID));
    }
}