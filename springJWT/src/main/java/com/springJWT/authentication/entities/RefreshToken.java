package com.springJWT.authentication.entities;

import com.springJWT.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a RefreshToken entity used to handle refresh tokens in JWT-based authentication.
 * A refresh token is used to obtain a new JWT once the current JWT has expired.
 */
@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "Please enter refresh token value!")
    private String refreshToken;

    @Column(nullable = false)
    private Integer expirationTime;

    @OneToOne
    private User user; // Link to the associated User entity

    // Constructor using @Builder to create an instance with the builder pattern
    @Builder
    public RefreshToken(Integer tokenId, String refreshToken, Integer expirationTime, User user) {
        this.tokenId = tokenId;
        this.refreshToken = refreshToken;
        this.expirationTime = expirationTime;
        this.user = user;
    }
}
