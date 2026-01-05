package org.cachewrapper.aggregate;

import lombok.*;
import org.cachewrapper.aggregate.mapper.UserCredentialsAggregateMapper;
import org.cachewrapper.data.domain.UserCredentialsEntity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserCredentialsAggregate implements Aggregate {

    private UUID userUUID;
    private String email;
    private String username;
    private String passwordHash;

    public UserCredentialsAggregate(
            @NotNull final String email,
            @NotNull final String username,
            @NotNull final String passwordHash
    ) {
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
    }
}