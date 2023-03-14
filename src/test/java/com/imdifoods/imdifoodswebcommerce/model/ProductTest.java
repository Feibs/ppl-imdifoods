package com.imdifoods.imdifoodswebcommerce.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
class ProductTest {

    @Test
    void productBuilderTest() {
        String name = "mockName";
        String description = "mockDescription";
        int stock = 3;
        Double price = 10000.0;
        Product product = Product.builder()
                .name(name)
                .description(description)
                .stock(stock)
                .price(price)
                .build();
        assertEquals("Product name", product.getName(), name);
        assertEquals("Product description", product.getDescription(), description);
        assertEquals("Product stock", product.getStock(), stock);
        assertEquals("Product price", product.getPrice(), price);
    }
}