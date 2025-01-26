package com.springJWT.dto;

import com.springJWT.model.enums.Role;
import com.springJWT.model.enums.UserState;

public record UserDTO(int id, String username, String password, Role role, UserState state) {
    // All required fields are present
}
