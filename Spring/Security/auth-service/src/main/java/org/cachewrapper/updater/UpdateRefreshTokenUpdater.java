package org.cachewrapper.updater;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.event.UpdateRefreshTokenEvent;
import org.cachewrapper.query.repository.UserCredentialsViewRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateRefreshTokenUpdater implements Updater<UpdateRefreshTokenEvent> {

    private final UserCredentialsViewRepository userCredentialsViewRepository;

    @Override
    @KafkaListener(topics = "update-refresh-token")
    public void update(UpdateRefreshTokenEvent event) {
        var userUUID = event.getUserUUID();
        var refreshTokenString = event.getRefreshTokenString();
        var userCredentialsView = userCredentialsViewRepository.findById(userUUID).orElseThrow();

        userCredentialsView.setRefreshTokenString(refreshTokenString);
        userCredentialsViewRepository.save(userCredentialsView);
    }
}