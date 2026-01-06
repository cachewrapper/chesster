package org.cachewrapper.data.service;

import lombok.RequiredArgsConstructor;
import org.cachewrapper.aggregate.UserAggregate;
import org.cachewrapper.aggregate.mapper.UserMapper;
import org.cachewrapper.data.domain.UserEntity;
import org.cachewrapper.data.repository.UserRepository;
import org.cachewrapper.token.domain.token.AccessToken;
import org.cachewrapper.token.filter.domain.SessionAuthentication;
import org.cachewrapper.token.service.token.AccessTokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AccessTokenService accessTokenService;

    public UserAggregate findById(@NotNull UUID userUUID) {
        return userRepository
                .findById(userUUID)
                .map(userMapper::toAggregate)
                .orElse(null);
    }

    public void uploadProfileAvatar(@NotNull SessionAuthentication authentication, @NotNull MultipartFile avatarFile) {
        final AccessToken accessToken = accessTokenService.getTokenFromString(authentication.getAccessTokenString());
        final UUID userUUID = accessToken.userUUID();

        final UserAggregate userAggregate = findById(userUUID);
        userAggregate.setAvatar(avatarFile);

        final UserEntity userEntity = userMapper.toEntity(userAggregate);
        userRepository.save(userEntity);
    }
}