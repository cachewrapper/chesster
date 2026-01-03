package org.cachewrapper.command.handler;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.aggregate.repository.UserCredentialsAggregateRepository;
import org.cachewrapper.command.domain.UpdateRefreshTokenCommand;
import org.cachewrapper.event.Event;
import org.cachewrapper.event.UpdateRefreshTokenEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateRefreshTokenCommandHandler implements CommandHandler<UpdateRefreshTokenCommand> {

    private final UserCredentialsAggregateRepository credentialsAggregateRepository;
    private final KafkaTemplate<String, Event> kafkaTemplate;

    @Override
    public void handleCommand(@NotNull UpdateRefreshTokenCommand command) {
        var userUUID = command.userUUID();
        var refreshTokenString = command.refreshTokenString();

        var userAggregate = credentialsAggregateRepository.findById(userUUID).orElseThrow();
        userAggregate.setRefreshTokenString(refreshTokenString);
        credentialsAggregateRepository.save(userAggregate);

        var updateRefreshTokenEvent = new UpdateRefreshTokenEvent(userUUID, refreshTokenString);
        kafkaTemplate.send("update-refresh-token", updateRefreshTokenEvent);
    }
}