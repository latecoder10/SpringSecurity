package com.springJWT.authentication.utils;

/**
 * The LoginRequest class is a data structure used to encapsulate the 
 * information needed for a user to log in to the application. 
 * It contains the user's username and password.
 * 
 * This class serves as a Data Transfer Object (DTO) to facilitate the 
 * transfer of login data between the client and the server.
 */
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    private String username; // Changed from 'email' to 'username' to be consistent with User class
    private String password;

    // No-argument constructor is provided by Lombok's @Data annotation
    // Builder is also provided by Lombok's @Builder annotation
}
