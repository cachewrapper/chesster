package org.cachewrapper.token.repository.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@Table(name = "user_refresh_tokens")
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenEntity {

    @Id
    @Column(name = "refresh_token_uuid")
    private UUID refreshTokenUUID;

    @Column(name = "user_uuid")
    private UUID userUUID;

    @Column(name = "refresh_token")
    private String refreshTokenString;
}