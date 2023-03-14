package com.imdifoods.imdifoodswebcommerce.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
class ProductTest {

    private String name = "mockName";
    private String description = "mockDescription";
    private int stock = 3;
    private Double price = 10000.0;
    private String imageId = "imageId";

    @Test
    void productBuilderComplete() {
        Product product = Product.builder()
                .name(name)
                .description(description)
                .stock(stock)
                .price(price)
                .imageId(imageId)
                .build();
        assertEquals("Product name", name, product.getName());
        assertEquals("Product description", description, product.getDescription());
        assertEquals("Product stock", stock, product.getStock());
        assertEquals("Product price", price, product.getPrice());
        assertEquals("Product imageId", imageId, product.getImageId());
    }

    @Test
    void productBuilderNoName() {
        name = null;
        Product.ProductBuilder product = Product.builder()
                .description(description)
                .stock(stock)
                .price(price);
        assertThrows(IllegalArgumentException.class, product::build);
    }

    @Test
    void productBuilderNoDescription() {
        description = null;
        Product.ProductBuilder product = Product.builder()
                .name(name)
                .stock(stock)
                .price(price);
        assertThrows(IllegalArgumentException.class, product::build);
    }

    @Test
    void productBuilderNegativeStock() {
        stock = -1;
        Product.ProductBuilder product = Product.builder()
                .name(name)
                .description(description)
                .stock(stock);
        assertThrows(IllegalArgumentException.class, product::build);
    }

    @Test
    void productBuilderNegativePrice() {
        price = -100000.0;
        Product.ProductBuilder product = Product.builder()
                .name(name)
                .description(description)
                .stock(stock)
                .price(price);
        assertThrows(IllegalArgumentException.class, product::build);
    }

    @Test
    void productBuilderNoImageId() {
        Product.ProductBuilder product = Product.builder()
                .name(name)
                .description(description)
                .stock(stock)
                .price(price);
        assertThrows(IllegalArgumentException.class, product::build);
    }
}