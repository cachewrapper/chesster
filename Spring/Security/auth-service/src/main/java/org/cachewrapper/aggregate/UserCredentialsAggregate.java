package org.cachewrapper.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
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