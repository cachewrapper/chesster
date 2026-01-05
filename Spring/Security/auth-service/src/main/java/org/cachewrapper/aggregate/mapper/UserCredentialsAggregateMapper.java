package org.cachewrapper.aggregate.mapper;

import org.cachewrapper.aggregate.UserCredentialsAggregate;
import org.cachewrapper.data.domain.UserCredentialsEntity;
import org.springframework.stereotype.Component;

@Component
public class UserCredentialsAggregateMapper implements AggregateMapper<UserCredentialsEntity, UserCredentialsAggregate> {

    @Override
    public UserCredentialsAggregate toAggregate(UserCredentialsEntity entity) {
        var userUUID = entity.getUserUUID();
        var email = entity.getEmail();
        var username = entity.getUsername();
        var passwordHash = entity.getPasswordHash();

        return new UserCredentialsAggregate(userUUID, email, username, passwordHash);
    }

    @Override
    public UserCredentialsEntity toEntity(UserCredentialsAggregate aggregate) {
        var userUUID = aggregate.getUserUUID();
        var email = aggregate.getEmail();
        var username = aggregate.getUsername();
        var passwordHash = aggregate.getPasswordHash();

        return new UserCredentialsEntity(userUUID, email, username, passwordHash);
    }
}
