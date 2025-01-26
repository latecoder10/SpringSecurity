package com.springJWT.authentication.utils;

/**
 * The RegisterRequest class is a data structure that holds the information 
 * required for a user registration process. This class serves as a Data 
 * Transfer Object (DTO) to facilitate the transfer of user registration 
 * data between the client and the server.
 */
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    private String username;  // Username for the user
    private String password;  // Password for the user

    // No-argument constructor is provided by Lombok's @Data annotation
    // Builder is also provided by Lombok's @Builder annotation
}
