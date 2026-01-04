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
    public void update(RemoveRefreshTokenEvent event) {
        System.out.println("Received remove-refresh-token event");

        var userUUID = event.getUserUUID();
        var refreshTokenString = event.getRefreshTokenString();
        var userCredentialsView = userCredentialsViewRepository.findById(userUUID).orElseThrow();

        var refreshTokenSet = userCredentialsView.getRefreshTokens();
        refreshTokenSet.remove(refreshTokenString);

        userCredentialsView.setRefreshTokens(refreshTokenSet);
        userCredentialsViewRepository.save(userCredentialsView);
    }
}