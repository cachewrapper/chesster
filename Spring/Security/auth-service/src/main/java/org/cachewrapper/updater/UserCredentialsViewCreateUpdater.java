package org.cachewrapper.updater;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.event.UserCredentialsCreateEvent;
import org.cachewrapper.query.domain.UserCredentialsView;
import org.cachewrapper.query.repository.UserCredentialsViewRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCredentialsViewCreateUpdater implements Updater<UserCredentialsCreateEvent> {

    private final UserCredentialsViewRepository userCredentialsViewRepository;

    @Override
    @KafkaListener(topics = "user-credentials-create", groupId = "account-events-group")
    @Transactional
    public void update(UserCredentialsCreateEvent event) {
        var userUUID = event.getUserUUID();
        var email = event.getEmail();
        var username = event.getUsername();
        var passwordHash = event.getPasswordHash();
        var refreshTokenString = event.getRefreshTokenString();

        var userCredentialsView = new UserCredentialsView(userUUID, email, username, passwordHash, refreshTokenString);
        userCredentialsViewRepository.save(userCredentialsView);
    }
}