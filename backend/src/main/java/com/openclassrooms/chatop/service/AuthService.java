package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.DTO.auth.AuthResponse;
import com.openclassrooms.chatop.DTO.auth.LoginRequest;
import com.openclassrooms.chatop.DTO.auth.RegisterRequest;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.repository.UserRepository;
import com.openclassrooms.chatop.security.JwtService;
import com.openclassrooms.chatop.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * Constructs the AuthService with required dependencies.
     *
     * @param userRepository        repository for user data persistence
     * @param passwordEncoder       encoder to hash user passwords securely
     * @param authenticationManager authentication manager to verify user credentials
     * @param jwtService            service to generate and validate JWT tokens
     */
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Registers a new user with the provided registration data.
     * <p>
     * Checks if the email is already in use, hashes the password,
     * sets creation date, and saves the user to the database.
     * </p>
     *
     * @param req the registration request containing user details
     * @throws IllegalArgumentException if the email is already registered
     */
    public void register(RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setEmail(req.getEmail());
        user.setName(req.getName());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setCreatedAt(LocalDate.now());

        userRepository.save(user);
    }

    /**
     * Authenticates a user using provided login credentials.
     * <p>
     * On successful authentication, generates a JWT token and returns
     * an AuthResponse containing token and user info.
     * </p>
     *
     * @param req the login request containing email and password
     * @return an AuthResponse containing JWT and user details
     * @throws IllegalArgumentException if authentication fails (invalid credentials)
     */
    public AuthResponse login(LoginRequest req) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails.getUsername());

            return new AuthResponse(
                    token,
                    "Bearer",
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getName()
            );

        } catch (AuthenticationException ex) {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }
}