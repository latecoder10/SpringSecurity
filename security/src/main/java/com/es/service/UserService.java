package com.es.service;

import java.util.List;

import com.es.dto.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO userDTO); // Use UserDTO for creating a user
    List<UserDTO> getAllUsers();
    UserDTO getUserById(int id);
    UserDTO updateUser(int id, UserDTO userDetails); // Use UserDTO for updating a user
    void deleteUser(int id);
}