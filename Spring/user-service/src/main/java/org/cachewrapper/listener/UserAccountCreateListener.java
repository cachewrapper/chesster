package org.cachewrapper.listener;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.event.impl.AccountCreateEvent;
import org.cachewrapper.data.domain.UserEntity;
import org.cachewrapper.data.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAccountCreateListener implements Listener<AccountCreateEvent> {

    private final UserRepository userRepository;

    @Override
    @KafkaListener(topics = "account-create", groupId = "account-events-group")
    public void listener(AccountCreateEvent event) {
        var userUUID = event.getUserUUID();
        var email = event.getEmail();
        var username = event.getUsername();

        var userEntity = new UserEntity(userUUID, email, username);
        userRepository.save(userEntity);
    }
}