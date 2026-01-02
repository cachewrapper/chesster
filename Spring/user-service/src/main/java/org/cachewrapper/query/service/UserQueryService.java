package org.cachewrapper.query.service;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.aggregate.UserAggregate;
import org.cachewrapper.aggregate.repository.UserAggregateRepository;
import org.cachewrapper.query.domain.UserViewDto;
import org.cachewrapper.query.repository.UserViewRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserQueryService implements QueryService<UserViewDto, UUID> {

    private final UserViewRepository userViewRepository;

    @Override
    public UserViewDto findById(UUID id) {
        return userViewRepository.findById(id).orElse(null);
    }
}