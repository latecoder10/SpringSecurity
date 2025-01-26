package com.springJWT.authentication.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springJWT.authentication.utils.AuthResponse;
import com.springJWT.authentication.utils.LoginRequest;
import com.springJWT.authentication.utils.RegisterRequest;
import com.springJWT.model.User;
import com.springJWT.model.UserPrincipal;
import com.springJWT.model.enums.Role;
import com.springJWT.model.enums.UserState;
import com.springJWT.repository.UserRepository;

/**
 * The AuthService class provides authentication and registration functionalities
 * for users in the application.
 * It handles the creation of user accounts, user login, and token generation.
 */
@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructs an instance of AuthService.
     *
     * @param passwordEncoder         The password encoder for encoding user passwords.
     * @param userRepository          The repository for managing User entities.
     * @param jwtService              The service for generating and validating JWTs.
     * @param refreshTokenService     The service for managing refresh tokens.
     * @param authenticationManager   The authentication manager for handling user authentication.
     */
    public AuthService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       JwtService jwtService,
                       RefreshTokenService refreshTokenService,
                       AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registers a new user in the application.
     *
     * @param registerRequest The request object containing user registration data.
     * @return An AuthResponse containing the access token and refresh token.
     */
    public AuthResponse register(RegisterRequest registerRequest) {
        // Create a new User entity from the registration request using builder pattern
        var user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER) // Default role for new users
                .state(UserState.ACTIVE) // Default state for new users
                .build();

        // Save the user to the database
        User savedUser = userRepository.save(user);
        
        // Generate access and refresh tokens for the registered user
        // Wrap the User entity in a UserPrincipal (implements UserDetails)
        var userPrincipal = new UserPrincipal(savedUser);
        var accessToken = jwtService.generateToken(userPrincipal);  // Pass UserPrincipal instead of User
        var refreshToken = refreshTokenService.createRefreshToken(savedUser.getUsername());

        // Return the tokens in the AuthResponse
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    /**
     * Authenticates a user and generates tokens upon successful login.
     *
     * @param loginRequest The request object containing user login credentials.
     * @return An AuthResponse containing the access token and refresh token.
     * @throws UsernameNotFoundException if the user with the given username does not exist.
     */
    public AuthResponse login(LoginRequest loginRequest) {
        // Authenticate the user using the provided username and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), 
                        loginRequest.getPassword())
        );

        // Retrieve the authenticated user from the repository
        var user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        // Generate access and refresh tokens for the logged-in user
        var userPrincipal = new UserPrincipal(user);
        var accessToken = jwtService.generateToken(userPrincipal);  // Pass UserPrincipal instead of User
        var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getUsername());

        // Return the tokens in the AuthResponse
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
}
