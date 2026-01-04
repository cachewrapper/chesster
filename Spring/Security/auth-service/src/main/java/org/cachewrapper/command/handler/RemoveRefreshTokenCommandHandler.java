package org.cachewrapper.command.handler;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.aggregate.repository.UserCredentialsAggregateRepository;
import org.cachewrapper.command.domain.RemoveRefreshTokenCommand;
import org.cachewrapper.event.Event;
import org.cachewrapper.event.RemoveRefreshTokenEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RemoveRefreshTokenCommandHandler implements CommandHandler<RemoveRefreshTokenCommand> {

    private final UserCredentialsAggregateRepository credentialsAggregateRepository;
    private final KafkaTemplate<String, Event> kafkaTemplate;

    @Override
    @Transactional
    public void handleCommand(@NotNull RemoveRefreshTokenCommand command) {
        var userUUID = command.userUUID();
        var refreshTokenString = command.refreshTokenString();

        var userAggregate = credentialsAggregateRepository.findById(userUUID).orElseThrow();
        userAggregate.removeRefreshToken(refreshTokenString);
        credentialsAggregateRepository.save(userAggregate);

        var removeRefreshTokenEvent = new RemoveRefreshTokenEvent(userUUID, refreshTokenString);
        kafkaTemplate.send("remove-refresh-token", removeRefreshTokenEvent);
    }
}