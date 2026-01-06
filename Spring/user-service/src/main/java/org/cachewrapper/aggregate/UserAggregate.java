package org.cachewrapper.aggregate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.cachewrapper.exception.avatar.AvatarNullException;
import org.cachewrapper.exception.avatar.AvatarSizeReachedException;
import org.jetbrains.annotations.NotNull;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAggregate implements Aggregate {

    private static final int AVATAR_MAX_FILE_SIZE_KB = 500_000;

    private UUID userUUID;
    private String email;
    private String username;

    @JsonProperty
    private byte[] avatar;

    @SneakyThrows
    public void setAvatar(MultipartFile avatarFile) {
        if (avatarFile == null || avatarFile.getSize() <= 0) {
            throw new AvatarNullException();
        }

        if (avatarFile.getSize() > AVATAR_MAX_FILE_SIZE_KB) {
            throw new AvatarSizeReachedException();
        }

        this.avatar = avatarFile.getBytes();
    }
}