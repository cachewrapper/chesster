package org.cachewrapper.query.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "user_credentials_view")
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialsView implements View {

    @Id
    @Column(name = "user_uuid")
    private UUID userUUID;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "refresh_token")
    @CollectionTable(
            name = "user_credentials_view_refresh_tokens",
            joinColumns = @JoinColumn(name = "user_uuid")
    )
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> refreshTokens;
}
