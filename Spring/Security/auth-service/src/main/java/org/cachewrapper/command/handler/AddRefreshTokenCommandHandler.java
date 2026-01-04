package org.cachewrapper.command.handler;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.command.domain.AddRefreshTokenCommand;
import org.cachewrapper.event.Event;
import org.cachewrapper.event.AddRefreshTokenEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AddRefreshTokenCommandHandler implements CommandHandler<AddRefreshTokenCommand> {

    private final KafkaTemplate<String, Event> kafkaTemplate;

    @Override
    @Transactional
    public void handleCommand(@NotNull AddRefreshTokenCommand command) {
        var userUUID = command.userUUID();
        var refreshTokenString = command.refreshTokenString();

        var updateRefreshTokenEvent = new AddRefreshTokenEvent(userUUID, refreshTokenString);
        kafkaTemplate.send("add-refresh-token", updateRefreshTokenEvent);
    }
}