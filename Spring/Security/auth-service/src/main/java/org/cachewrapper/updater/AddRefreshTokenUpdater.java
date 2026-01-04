package org.cachewrapper.updater;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.event.AddRefreshTokenEvent;
import org.cachewrapper.query.repository.UserCredentialsViewRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddRefreshTokenUpdater implements Updater<AddRefreshTokenEvent> {

    private final UserCredentialsViewRepository userCredentialsViewRepository;

    @Override
    @KafkaListener(topics = "add-refresh-token", groupId = "account-events-group")
    @Transactional
    public void update(AddRefreshTokenEvent event) {
        var userUUID = event.getUserUUID();
        var refreshTokenString = event.getRefreshTokenString();

        userCredentialsViewRepository.insertRefreshToken(userUUID, refreshTokenString);
    }
}