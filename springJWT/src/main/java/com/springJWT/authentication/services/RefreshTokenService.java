package com.springJWT.authentication.services;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springJWT.authentication.entities.RefreshToken;
import com.springJWT.authentication.repositories.RefreshTokenRepository;
import com.springJWT.exception.RefreshTokenExpiredException;
import com.springJWT.exception.RefreshTokenNotFoundException;
import com.springJWT.model.User;
import com.springJWT.repository.UserRepository;

@Service
public class RefreshTokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        RefreshToken existingRefreshToken = refreshTokenRepository.findByUser(user);
        if (existingRefreshToken != null) {
            return existingRefreshToken;
        }

        long refreshTokenValidity = 30 * 60 * 1000; // 30 minutes
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(UUID.randomUUID().toString())
                .expirationTime((int) Instant.now().plusMillis(refreshTokenValidity).getEpochSecond())
                .user(user)
                .build();

        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyRefreshToken(String refreshToken) {
        RefreshToken refToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RefreshTokenNotFoundException("Refresh token not found!"));

        if (refToken.getExpirationTime().compareTo((int) Instant.now().getEpochSecond()) < 0) {
            refreshTokenRepository.delete(refToken);
            throw new RefreshTokenExpiredException("Refresh token expired");
        }

        return refToken;
    }

    public void deleteRefreshToken(String refreshToken) {
        RefreshToken refToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RefreshTokenNotFoundException("Refresh token not found!"));

        refreshTokenRepository.delete(refToken);
    }
}
