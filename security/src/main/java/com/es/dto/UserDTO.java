package com.es.dto;

import com.es.model.enums.Role;
import com.es.model.enums.UserState;

public record UserDTO(int id, String username, String password, Role role, UserState state) {
    // All required fields are present
}
