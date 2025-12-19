package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.DTO.auth.AuthResponse;
import com.openclassrooms.chatop.DTO.auth.LoginRequest;
import com.openclassrooms.chatop.DTO.auth.RegisterRequest;
import com.openclassrooms.chatop.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registers a new user.
     *
     * <p>
     * Creates a new user account using the provided registration data.
     * </p>
     *
     * @param req the registration request containing user information
     * @return a success message or an error response if registration fails
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        try {
            authService.register(req);
            return ResponseEntity.ok(Map.of("message", "User registered"));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    /**
     * Authenticates a user and returns a JWT token.
     *
     * <p>
     * Validates user credentials and returns an authentication response
     * containing the JWT token if successful.
     * </p>
     *
     * @param req the login request containing email and password
     * @return authentication token or 401 error if credentials are invalid
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            AuthResponse response = authService.login(req);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", ex.getMessage()));
        }
    }
}
