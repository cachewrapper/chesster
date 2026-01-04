package org.cachewrapper.command.handler;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.aggregate.UserCredentialsAggregate;
import org.cachewrapper.aggregate.repository.UserCredentialsAggregateRepository;
import org.cachewrapper.command.domain.AccountCreateCommand;
import org.cachewrapper.event.Event;
import org.cachewrapper.event.UserCredentialsCreateEvent;
import org.cachewrapper.event.impl.AccountCreateEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AccountCreateCommandHandler implements CommandHandler<AccountCreateCommand> {

    private final UserCredentialsAggregateRepository credentialsAggregateRepository;
    private final KafkaTemplate<String, Event> kafkaTemplate;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void handleCommand(@NotNull AccountCreateCommand command) {
        var userUUID = command.userUUID();
        var email = command.email();
        var username = command.username();
        var refreshTokenString = command.refreshTokenString();

        var password = command.password();
        var passwordHash = Objects.requireNonNull(passwordEncoder.encode(password));

        var refreshTokenList = new ArrayList<>(List.of(refreshTokenString));
        var userAggregate = new UserCredentialsAggregate(userUUID, email, username, passwordHash, refreshTokenList);
        credentialsAggregateRepository.save(userAggregate);

        var accountCreateEvent = new AccountCreateEvent(userUUID, email, username);
        kafkaTemplate.send("account-create", accountCreateEvent);

        var userCredentialsCreateEvent = new UserCredentialsCreateEvent(userUUID, email, username, passwordHash, refreshTokenString);
        kafkaTemplate.send("user-credentials-create", userCredentialsCreateEvent);
    }
}