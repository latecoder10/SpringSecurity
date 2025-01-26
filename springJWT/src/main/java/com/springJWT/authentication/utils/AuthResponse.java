package com.springJWT.authentication.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The AuthResponse class is a data transfer object (DTO) that represents the
 * response returned to the client during the authentication process.
 * It contains the access token and refresh token necessary for user sessions.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String accessToken;   // The JWT access token
    private String refreshToken;  // The refresh token for session renewal
}
