package org.cachewrapper.data.service;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.aggregate.UserCredentialsAggregate;
import org.cachewrapper.aggregate.mapper.UserCredentialsAggregateMapper;
import org.cachewrapper.data.repository.UserCredentialsEntityRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCredentialsService {

    private final UserCredentialsAggregateMapper userCredentialsAggregateMapper;
    private final UserCredentialsEntityRepository userCredentialsEntityRepository;

    @Nullable
    public UserCredentialsAggregate findById(UUID userUUID) {
        return userCredentialsEntityRepository
                .findById(userUUID)
                .map(userCredentialsAggregateMapper::toAggregate)
                .orElse(null);
    }

    @Nullable
    public UserCredentialsAggregate findByEmail(String email) {
        return userCredentialsEntityRepository
                .findByEmail(email)
                .map(userCredentialsAggregateMapper::toAggregate)
                .orElse(null);
    }

    @Nullable
    public UserCredentialsAggregate findByUsername(String username) {
        return userCredentialsEntityRepository
                .findByUsername(username)
                .map(userCredentialsAggregateMapper::toAggregate)
                .orElse(null);
    }

    @NotNull
    public UserCredentialsAggregate save(UserCredentialsAggregate userCredentialsAggregate) {
        final var userCredentialsEntity = userCredentialsAggregateMapper.toEntity(userCredentialsAggregate);
        final var savedUserCredentialsEntity = userCredentialsEntityRepository.save(userCredentialsEntity);

        return userCredentialsAggregateMapper.toAggregate(savedUserCredentialsEntity);
    }
}