package com.imdifoods.imdifoodswebcommerce.exception;

import lombok.Getter;

@Getter
public class OverflowPageableException extends RuntimeException {
    private final String message;
    private final String validPage;

    public OverflowPageableException(String message, String validPage) {
        super(message);
        this.message = message;
        this.validPage = validPage;
    }
}
