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

    private static final int MAX_TOKENS = 10;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "refresh_token")
    @CollectionTable(
            name = "user_credentials_aggregate_refresh_tokens",
            joinColumns = @JoinColumn(name = "aggregate_uuid")
    )
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> refreshTokens;

    public UserCredentialsAggregate(
            @NotNull UUID userUUID,
            @NotNull String email,
            @NotNull String username,
            @NotNull String passwordHash,
            @NotNull List<String> refreshTokens
    ) {
        super(userUUID);
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.refreshTokens = refreshTokens;
    }

    public void addRefreshToken(@NotNull String token) {
        if (token.isEmpty()) {
            throw new RefreshTokenEmptyException();
        }

        if (refreshTokens.size() >= MAX_TOKENS) {
            refreshTokens.removeFirst();
        }

        refreshTokens.add(token);
    }

    public void removeRefreshToken(@NotNull String token) {
        refreshTokens.remove(token);
    }

    public boolean hasRefreshToken(@NotNull String token) {
        return refreshTokens.contains(token);
    }
}