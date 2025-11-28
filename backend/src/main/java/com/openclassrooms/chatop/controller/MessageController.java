package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.DTO.MessageRequest;
import com.openclassrooms.chatop.model.Message;
import com.openclassrooms.chatop.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/api/messages")
    public Message createMessage(@RequestBody MessageRequest request) {
        return messageService.createMessage(request);
    }
}
