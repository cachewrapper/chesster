package org.cachewrapper.query.service;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.query.domain.UserCredentialsView;
import org.cachewrapper.query.repository.UserCredentialsViewRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCredentialsQueryService implements QueryService<UserCredentialsView, UUID> {

    private final UserCredentialsViewRepository userCredentialsViewRepository;

    @Override
    public UserCredentialsView findById(UUID id) {
        return userCredentialsViewRepository.findById(id).orElse(null);
    }

    public UserCredentialsView findByEmail(String email) {
        return userCredentialsViewRepository.findUserCredentialsViewByEmail(email).orElse(null);
    }
}