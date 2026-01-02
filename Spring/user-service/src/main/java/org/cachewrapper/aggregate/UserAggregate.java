package org.cachewrapper.aggregate;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "user_aggregate")
@NoArgsConstructor
public class UserAggregate extends Aggregate {

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    public UserAggregate(UUID userUUID,  String email, String username) {
        super(userUUID);
        this.email = email;
        this.username = username;
    }
}