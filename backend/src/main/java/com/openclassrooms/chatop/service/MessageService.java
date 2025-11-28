package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.DTO.MessageRequest;
import com.openclassrooms.chatop.model.Message;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.repository.MessageRepository;
import com.openclassrooms.chatop.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserService userService;

    public Message createMessage(MessageRequest request) {
        Message message = new Message();
        User user = userService.getCurrentUser();
        Rental rental = rentalRepository.findById(request.getRentalId())
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        message.setRental(rental);
        message.setUser(user);
        message.setMessage(request.getMessage());
        message.setCreatedAt(LocalDate.now());

        return messageRepository.save(message);
    }
}
