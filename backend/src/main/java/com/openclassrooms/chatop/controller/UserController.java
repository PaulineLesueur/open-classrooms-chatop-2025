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

    @GetMapping("auth/me")
    public UserResponse getMe() {
        return userService.getCurrentUserResponse();
    }

    @GetMapping("/user/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
