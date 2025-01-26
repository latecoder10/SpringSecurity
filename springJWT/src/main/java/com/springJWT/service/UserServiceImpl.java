package com.springJWT.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springJWT.dto.UserDTO;
import com.springJWT.exception.CustomAccessDeniedException;
import com.springJWT.exception.UserAlreadyExistsException;
import com.springJWT.exception.UserNotFoundException;
import com.springJWT.mapper.UserMapper;
import com.springJWT.model.User;
import com.springJWT.model.UserPrincipal;
import com.springJWT.model.enums.Role;
import com.springJWT.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    // Constructor Injection
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
		this.userMapper = userMapper;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        // Convert DTO to Entity
        User user = userMapper.toEntity(userDTO);

        // Check if user with the same username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("User already exists with username: " + user.getUsername());
        }

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);  // Return DTO after saving
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDTO).toList();
    }

    @Override
    public UserDTO getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO updateUser(int id, UserDTO userDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        // Allow only if the user is admin or updating their own account
        if (!principal.getUser().getRole().equals(Role.ADMIN) && principal.getUser().getId() != id) {
            throw new CustomAccessDeniedException("You are not authorized to update this user.", "ACCESS_DENIED");
        }


        // Proceed with the update
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update fields
        existingUser.setUsername(userDTO.username());

        // Encrypt the password before updating it
        if (userDTO.password() != null && !userDTO.password().isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(userDTO.password());
            existingUser.setPassword(encryptedPassword);
        }

        existingUser.setRole(userDTO.role());
        existingUser.setState(userDTO.state());

        userRepository.save(existingUser);

        return userMapper.toDTO(existingUser);
    }



    @Override
    public void deleteUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

        // Get the currently authenticated user
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Authorization check: Only ADMINs or the owner of the account can delete
        if (!principal.getUser().getRole().equals(Role.ADMIN) && principal.getUser().getId() != id) {
            throw new CustomAccessDeniedException("You are not authorized to delete this user.", "ACCESS_DENIED");
        }

        userRepository.delete(user);
    }

}
