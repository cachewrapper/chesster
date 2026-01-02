package org.cachewrapper.query.repository;

import org.cachewrapper.query.domain.UserViewDto;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserViewRepository extends ViewRepository<UserViewDto, UUID> {}