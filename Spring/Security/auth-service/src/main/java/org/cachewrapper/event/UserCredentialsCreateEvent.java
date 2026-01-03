package org.cachewrapper.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.query.domain.UserCredentialsView;

import java.lang.ref.PhantomReference;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class UserCredentialsCreateEvent implements Event {
    private final UUID userUUID;
    private final String email;
    private final String username;
    private final String passwordHash;
}