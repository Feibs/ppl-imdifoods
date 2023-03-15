package com.imdifoods.imdifoodswebcommerce.controller;

import com.imdifoods.imdifoodswebcommerce.model.Message;
import com.imdifoods.imdifoodswebcommerce.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<Message> saveMessage(@RequestParam("sender-email") String email,
                                               @RequestParam("sender-message") String message) {
        Message messageObject = messageService.saveMessage(email, message);
        return ResponseEntity.ok(messageObject);
    }
}
