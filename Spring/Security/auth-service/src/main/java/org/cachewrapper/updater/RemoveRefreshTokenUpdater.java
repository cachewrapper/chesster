package org.cachewrapper.updater;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.event.RemoveRefreshTokenEvent;
import org.cachewrapper.query.repository.UserCredentialsViewRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveRefreshTokenUpdater implements Updater<RemoveRefreshTokenEvent> {

    private final UserCredentialsViewRepository userCredentialsViewRepository;

    @Override
    @KafkaListener(topics = "remove-refresh-token", groupId = "account-events-group")
    @Transactional
    public void update(RemoveRefreshTokenEvent event) {
        var userUUID = event.getUserUUID();
        var refreshTokenString = event.getRefreshTokenString();

        userCredentialsViewRepository.deleteRefreshToken(userUUID, refreshTokenString);
    }
}