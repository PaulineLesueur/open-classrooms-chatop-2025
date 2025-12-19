package com.openclassrooms.chatop.service;

import com.openclassrooms.chatop.DTO.MessageRequest;
import com.openclassrooms.chatop.DTO.MessageResponse;
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

    /**
     * Converts a Message entity to a MessageResponse DTO.
     *
     * @param message the Message entity to convert
     * @return the corresponding MessageResponse DTO
     */
    private MessageResponse convertToMessageResponse(Message message) {
        MessageResponse dto = new MessageResponse();
        dto.setId(message.getId());
        dto.setRentalId(message.getRental().getId());
        dto.setUserId(message.getUser().getId());
        dto.setMessage(message.getMessage());
        dto.setCreatedAt(message.getCreatedAt());
        dto.setUpdatedAt(message.getUpdatedAt());
        return dto;
    }

    /**
     * Creates a new message associated with a rental and the currently authenticated user.
     *
     * @param request the message request DTO containing rental ID and message text
     * @return the saved message as a MessageResponse DTO
     * @throws RuntimeException if the rental specified in the request does not exist
     */
    public MessageResponse createMessage(MessageRequest request) {
        Message message = new Message();
        User user = userService.getCurrentUser();
        Rental rental = rentalRepository.findById(request.getRentalId())
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        message.setRental(rental);
        message.setUser(user);
        message.setMessage(request.getMessage());
        message.setCreatedAt(LocalDate.now());

        Message savedMessage = messageRepository.save(message);

        return convertToMessageResponse(message);
    }
}