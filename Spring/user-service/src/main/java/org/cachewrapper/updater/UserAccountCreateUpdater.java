package org.cachewrapper.updater;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.aggregate.UserAggregate;
import org.cachewrapper.aggregate.repository.UserAggregateRepository;
import org.cachewrapper.event.impl.AccountCreateEvent;
import org.cachewrapper.listener.Listener;
import org.cachewrapper.query.domain.UserViewDto;
import org.cachewrapper.query.repository.UserViewRepository;
import org.cachewrapper.query.repository.ViewRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAccountCreateUpdater implements Listener<AccountCreateEvent> {

    private final UserAggregateRepository userAggregateRepository;
    private final UserViewRepository userViewRepository;

    @Override
    @KafkaListener(topics = "account-create", groupId = "account-events-group")
    @Transactional
    public void listener(AccountCreateEvent event) {
        var userUUID = event.getUserUUID();
        var email = event.getEmail();
        var username = event.getUsername();

        UserAggregate userAggregate = new UserAggregate(userUUID, email, username);
        userAggregateRepository.save(userAggregate);

        var userViewDto = new UserViewDto(userUUID, email, username);
        userViewRepository.save(userViewDto);
    }
}