package asia.vatek.project.service;


import asia.vatek.project.entity.RefreshTokenEntity;
import asia.vatek.project.entity.UserEntity;

import javax.transaction.Transactional;
import java.util.Optional;

public interface RefreshTokenService {
    Optional<RefreshTokenEntity> findByToken(String token);

    RefreshTokenEntity createRefreshToken(UserEntity userEntity);

    RefreshTokenEntity verifyExpiration(RefreshTokenEntity token);

    @Transactional
    Long deleteByUserId(Long userId);
}
