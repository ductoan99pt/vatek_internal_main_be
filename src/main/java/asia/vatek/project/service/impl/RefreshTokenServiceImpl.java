package asia.vatek.project.service.impl;

import asia.vatek.project.entity.RefreshTokenEntity;
import asia.vatek.project.entity.UserEntity;
import asia.vatek.project.jwt.exception.ErrorResponse;
import asia.vatek.project.jwt.exception.ProductException;
import asia.vatek.project.jwt.exception.TokenRefreshException;
import asia.vatek.project.respository.RefreshTokenRepository;
import asia.vatek.project.respository.UserRepository;
import asia.vatek.project.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${catdev.app.refreshTokenExpiration}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshTokenEntity createRefreshToken(UserEntity userEntity) {
        if(userEntity == null){
            throw new ProductException(new ErrorResponse());
        }

        RefreshTokenEntity refreshToken = new RefreshTokenEntity();

        refreshToken.setUserEntity(userEntity);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    @Override
    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().compareTo(Instant.now()) <= 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Override
    @Transactional
    public Long deleteByUserId(Long userId) {
        refreshTokenRepository.deleteById(userId);
        return userId;
    }
}
