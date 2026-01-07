package org.cachewrapper.socket.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameConnectionHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> webSocketSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession webSocketSession) throws Exception {
        super.afterConnectionEstablished(webSocketSession);

        final String sessionId = webSocketSession.getId();
        webSocketSessions.put(sessionId, webSocketSession);
    }

    @Override
    public void afterConnectionClosed(
            @NotNull WebSocketSession webSocketSession,
            @NotNull CloseStatus status
    ) throws Exception {
        super.afterConnectionClosed(webSocketSession, status);

        final String sessionId = webSocketSession.getId();
        webSocketSessions.remove(sessionId, webSocketSession);
    }

    @Override
    public void handleMessage(
            @NotNull WebSocketSession session,
            @NotNull WebSocketMessage<?> message
    ) throws Exception {
        super.handleMessage(session, message);

        final String sessionId = session.getId();
        webSocketSessions.get(sessionId).sendMessage(new TextMessage(message.getPayload().toString()));
    }
}