package org.cachewrapper.updater;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.aggregate.repository.UserCredentialsAggregateRepository;
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
    public void update(AddRefreshTokenEvent event) {
        System.out.println("Received add-refresh-token event");

        var userUUID = event.getUserUUID();
        var refreshTokenString = event.getRefreshTokenString();
        var userCredentialsView = userCredentialsViewRepository.findById(userUUID).orElseThrow();

        var refreshTokenSet = userCredentialsView.getRefreshTokens();
        refreshTokenSet.add(refreshTokenString);

        userCredentialsView.setRefreshTokens(refreshTokenSet);
        userCredentialsViewRepository.save(userCredentialsView);
    }
}