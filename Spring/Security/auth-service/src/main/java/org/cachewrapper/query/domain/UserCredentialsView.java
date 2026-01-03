package org.cachewrapper.query.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
