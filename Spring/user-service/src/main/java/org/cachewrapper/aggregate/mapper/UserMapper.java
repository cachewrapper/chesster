package org.cachewrapper.aggregate.mapper;

import org.cachewrapper.aggregate.UserAggregate;
import org.cachewrapper.query.domain.UserViewDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements AggregateMapper<UserViewDto, UserAggregate> {

    @Override
    public UserAggregate toAggregate(UserViewDto entity) {
        var userUUID = entity.getUserUUID();
        var email = entity.getEmail();
        var username = entity.getUsername();

        return new UserAggregate(userUUID, email, username);
    }

    @Override
    public UserViewDto toEntity(UserAggregate aggregate) {
        var aggregateUUID = aggregate.getAggregateUUID();
        var email = aggregate.getEmail();
        var username = aggregate.getUsername();

        return new UserViewDto(aggregateUUID, email, username);
    }
}
