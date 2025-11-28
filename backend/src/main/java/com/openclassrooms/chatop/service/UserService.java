package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User getCurrentUser() {
        User connectedUser = new User();
        connectedUser.setId(1L);
        connectedUser.setName("User Test");
        connectedUser.setEmail("user@test.com");
        connectedUser.setPassword("123");

        return connectedUser;
    }
}
