package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.DTO.UserResponse;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves the currently authenticated user.
     *
     * @return the User entity representing the current user
     * @throws RuntimeException if no user is authenticated or found
     */
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UserDetails)) {
            throw new RuntimeException("No authenticated user");
        }
        String email = ((UserDetails) auth.getPrincipal()).getUsername();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Converts a User entity to a UserResponse DTO.
     *
     * @param user the User entity to convert
     * @return the corresponding UserResponse DTO
     */
    private UserResponse convertToUserResponse(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }

    /**
     * Retrieves the current authenticated user as a UserResponse DTO.
     *
     * @return the UserResponse DTO of the current user
     */
    public UserResponse getCurrentUserResponse() {
        User user = getCurrentUser();
        return convertToUserResponse(user);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the UserResponse DTO for the specified user
     * @throws RuntimeException if no user is found with the given ID
     */
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return convertToUserResponse(user);
    }
}