package com.imdifoods.imdifoodswebcommerce.service;

import com.imdifoods.imdifoodswebcommerce.model.Message;
import com.imdifoods.imdifoodswebcommerce.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Override
    public Message saveMessage(String email, String message) {
        Message messageObject = Message.builder()
                .email(email)
                .message(message)
                .build();
        messageRepository.save(messageObject);
        return messageObject;
    }
}
