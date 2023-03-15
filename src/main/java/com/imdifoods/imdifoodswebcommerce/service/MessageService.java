package com.imdifoods.imdifoodswebcommerce.service;

import com.imdifoods.imdifoodswebcommerce.model.Message;

public interface MessageService {
    Message saveMessage(String email, String message);
}
