package com.catdev.project.service;


import com.vatek.project.entity.RefreshTokenEntity;
import com.vatek.project.entity.UserEntity;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {
    Optional<RefreshTokenEntity> findByToken(String token);

    RefreshTokenEntity createRefreshToken(UserEntity userEntity);

    RefreshTokenEntity verifyExpiration(RefreshTokenEntity token);

    @Transactional
    Long deleteByUserId(Long userId);
}
