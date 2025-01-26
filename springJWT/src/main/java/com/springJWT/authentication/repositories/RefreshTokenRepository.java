package com.springJWT.authentication.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springJWT.authentication.entities.RefreshToken;
import com.springJWT.model.User;

/**
 * Repository interface for managing RefreshToken entities.
 * Provides methods for CRUD operations and custom queries.
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    
    /**
     * Finds a RefreshToken entity by the refresh token string.
     *
     * @param refreshToken the refresh token string to search for
     * @return an Optional containing the RefreshToken entity if found, otherwise empty
     */
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    
 // Define a method to find a RefreshToken by associated User
    RefreshToken findByUser(User user);
}
