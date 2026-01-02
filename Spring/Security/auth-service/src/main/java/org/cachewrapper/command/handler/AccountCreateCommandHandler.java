package org.cachewrapper.command.handler;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.aggregate.UserCredentialsAggregate;
import org.cachewrapper.aggregate.repository.UserCredentialsAggregateRepository;
import org.cachewrapper.command.domain.AccountCreateCommand;
import org.cachewrapper.event.Event;
import org.cachewrapper.event.impl.AccountCreateEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

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

        var password = command.password();
        var passwordHash = Objects.requireNonNull(passwordEncoder.encode(password));

        var userAggregate = new UserCredentialsAggregate(userUUID, passwordHash, email, username);
        var savedUserCredentialsAggregate = credentialsAggregateRepository.save(userAggregate);

        var accountCreateEvent = new AccountCreateEvent(savedUserCredentialsAggregate.getAggregateUUID(), username, email);
        kafkaTemplate.send("account-create", accountCreateEvent);
    }
}