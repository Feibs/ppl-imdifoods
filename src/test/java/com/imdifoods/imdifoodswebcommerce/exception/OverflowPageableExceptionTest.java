package com.imdifoods.imdifoodswebcommerce.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class OverflowPageableExceptionTest {

    private final String MSG = "message";
    private final String VALID_PAGE = "1";

    void exceptionThrower() {
        throw new OverflowPageableException(MSG, VALID_PAGE);
    }

    @Test
    void testEquality() {
        try {
            exceptionThrower();
        } catch (OverflowPageableException e) {
            assertEquals(MSG, e.getMessage());
            assertEquals(VALID_PAGE, e.getValidPage());
        }
    }
}
