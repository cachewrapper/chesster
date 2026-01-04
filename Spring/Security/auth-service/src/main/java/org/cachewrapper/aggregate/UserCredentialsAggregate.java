package org.cachewrapper.aggregate;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.cachewrapper.exception.RefreshTokenEmptyException;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
@Entity
@Table(name = "user_credentials_aggregate")
@NoArgsConstructor
public class UserCredentialsAggregate extends Aggregate {

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    public UserCredentialsAggregate(
            @NotNull UUID userUUID,
            @NotNull String email,
            @NotNull String username,
            @NotNull String passwordHash
    ) {
        super(userUUID);
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
    }
}