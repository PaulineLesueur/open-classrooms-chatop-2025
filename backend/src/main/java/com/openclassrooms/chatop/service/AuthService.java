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

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

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
