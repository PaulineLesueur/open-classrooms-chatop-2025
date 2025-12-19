package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.DTO.UserResponse;
import com.openclassrooms.chatop.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves the currently authenticated user's details.
     *
     * @return a UserResponse DTO with current user information
     */
    @GetMapping("auth/me")
    public UserResponse getMe() {
        return userService.getCurrentUserResponse();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return a UserResponse DTO with the requested user's information
     */
    @GetMapping("/user/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
