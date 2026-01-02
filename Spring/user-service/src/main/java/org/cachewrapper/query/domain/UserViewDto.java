package org.cachewrapper.query.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Table(name = "user_view")
@NoArgsConstructor
@AllArgsConstructor
public class UserViewDto implements View {

    @Id
    @Column(name = "user_uuid")
    private UUID userUUID;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;
}