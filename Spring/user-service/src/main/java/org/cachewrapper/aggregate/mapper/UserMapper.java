package org.cachewrapper.aggregate.mapper;

import org.cachewrapper.aggregate.UserAggregate;
import org.cachewrapper.data.domain.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements AggregateMapper<UserEntity, UserAggregate> {

    @Override
    public UserAggregate toAggregate(UserEntity entity) {
        var userUUID = entity.getUserUUID();
        var email = entity.getEmail();
        var username = entity.getUsername();
        var avatar = entity.getAvatar();

        return new UserAggregate(userUUID, email, username, avatar);
    }

    @Override
    public UserEntity toEntity(UserAggregate aggregate) {
        var userUUID = aggregate.getUserUUID();
        var email = aggregate.getEmail();
        var username = aggregate.getUsername();
        var avatar = aggregate.getAvatar();

        return new UserEntity(userUUID, email, username, avatar);
    }
}
