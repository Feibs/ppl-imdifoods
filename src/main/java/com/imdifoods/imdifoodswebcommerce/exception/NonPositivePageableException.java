package com.imdifoods.imdifoodswebcommerce.exception;

import lombok.Getter;

@Getter
public class NonPositivePageableException extends RuntimeException {
    private final String message;

    public NonPositivePageableException(String message) {
        super(message);
        this.message = message;
    }
}
