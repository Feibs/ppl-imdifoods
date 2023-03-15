package com.imdifoods.imdifoodswebcommerce.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NonPositivePageableExceptionTest {

    private final String MSG = "message";

    void exceptionThrower() {
        throw new NonPositivePageableException(MSG);
    }

    @Test
    void testMessage() {
        try {
            exceptionThrower();
        } catch (NonPositivePageableException e) {
            assertEquals(MSG, e.getMessage());
        }
    }
}
