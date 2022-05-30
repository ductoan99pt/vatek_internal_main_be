package com.catdev.project.respository;

import com.vatek.project.entity.RefreshTokenEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findById(Long id);

    Optional<RefreshTokenEntity> findByToken(String token);
}
