package com.imdifoods.imdifoodswebcommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ImdifoodsWebcommerceApplicationTests {

    @Test
    void contextLoads() {
        ImdifoodsWebcommerceApplication.main(new String[]{});
        assertTrue(true, "App test");
    }

}
