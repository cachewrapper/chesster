package org.cachewrapper.data.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "user_uuid")
    private UUID userUUID;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "avatar")
    private byte[] avatar;

    public UserEntity(@NotNull UUID userUUID, @NotNull String email, @NotNull String username) {
        this.userUUID = userUUID;
        this.email = email;
        this.username = username;
    }
}