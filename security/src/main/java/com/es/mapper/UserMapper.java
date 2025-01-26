package com.es.mapper;

import com.es.dto.UserDTO;
import com.es.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        // Convert the User entity to UserDTO
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(), // Add role to DTO
                user.getState()  // Add state to DTO
        );
    }

    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        // Convert the UserDTO to User entity
        return new User(
                userDTO.id(),
                userDTO.username(),
                userDTO.password(),
                userDTO.role(),   // Map role from DTO to entity
                userDTO.state()   // Map state from DTO to entity
        );
    }
}
