package com.imdifoods.imdifoodswebcommerce.service;

import com.imdifoods.imdifoodswebcommerce.model.Message;
import com.imdifoods.imdifoodswebcommerce.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class MessageServiceTest {

    @Autowired
    MessageService messageService;

    @MockBean
    MessageRepository messageRepository;

    private final String SENDER_EMAIL = "test@gmail.com";
    private final String SENDER_MESSAGE = "this is a test message over here";

    @Test
    void testSaveMessage() {
        Message message = messageService.saveMessage(SENDER_EMAIL, SENDER_MESSAGE);
        verify(messageRepository).save(message);
    }

    @Test
    void testSaveInvalidMessage() {
        assertThrows(IllegalArgumentException.class, () -> messageService.saveMessage("", ""));
    }
}
