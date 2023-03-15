package com.imdifoods.imdifoodswebcommerce.controller;

import com.imdifoods.imdifoodswebcommerce.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@WebMvcTest(MessageController.class)
public class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    private final String SENDER_EMAIL = "test@gmail.com";
    private final String SENDER_MESSAGE = "this is a test message over here";

    @Test
    void postMessageTest() throws Exception {
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders
                .post("/message/send")
                .param("sender-email", SENDER_EMAIL)
                .param("sender-message", SENDER_MESSAGE);

        MvcResult result = mockMvc.perform(postRequest).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void whenPostMessage_ShouldSaveMessage() throws Exception {
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders
                .post("/message/send")
                .param("sender-email", SENDER_EMAIL)
                .param("sender-message", SENDER_MESSAGE);
        MvcResult result = mockMvc.perform(postRequest).andReturn();

        verify(messageService).saveMessage(SENDER_EMAIL, SENDER_MESSAGE);
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void whenPostMessageNoEmail_ReturnBadRequest() throws Exception {
        MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders
                .post("/message/send")
                .param("sender-message", SENDER_MESSAGE);
        MvcResult result = mockMvc.perform(postRequest).andReturn();

        assertEquals(400, result.getResponse().getStatus());
    }
}

