package org.cachewrapper.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.cachewrapper.exception.RefreshTokenEmptyException;
import org.jetbrains.annotations.NotNull;

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

    @Column(name = "refresh_token")
    private String refreshTokenString;

    public UserCredentialsAggregate(
            @NotNull UUID userUUID,
            @NotNull String email,
            @NotNull String username,
            @NotNull String passwordHash,
            @NotNull String refreshTokenString
    ) {
        super(userUUID);
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.refreshTokenString = refreshTokenString;
    }

    public void setRefreshTokenString(@NotNull String refreshTokenString) {
        if (refreshTokenString.isEmpty()) {
            throw new RefreshTokenEmptyException();
        }

        this.refreshTokenString = refreshTokenString;
    }
}