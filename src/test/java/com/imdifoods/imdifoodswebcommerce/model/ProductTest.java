package com.imdifoods.imdifoodswebcommerce.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
class ProductTest {

    private final String IMAGEID = "imageId";
    private String NAME = "mockName";
    private String DESCRIPTION = "mockDescription";
    private String COMPOSITION = "mockComposition";
    private int STOCK = 3;
    private Double PRICE = 10000.0;

    @Test
    void productBuilderComplete() {
        Product product = Product.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .composition(COMPOSITION)
                .stock(STOCK)
                .price(PRICE)
                .imageId(IMAGEID)
                .build();
        assertEquals("Product name", NAME, product.getName());
        assertEquals("Product description", DESCRIPTION, product.getDescription());
        assertEquals("Product composition", COMPOSITION, product.getComposition());
        assertEquals("Product stock", STOCK, product.getStock());
        assertEquals("Product price", PRICE, product.getPrice());
        assertEquals("Product imageId", IMAGEID, product.getImageId());
    }

    @Test
    void productBuilderNoName() {
        NAME = null;
        Product.ProductBuilder product = Product.builder()
                .description(DESCRIPTION)
                .stock(STOCK)
                .price(PRICE);
        assertThrows(IllegalArgumentException.class, product::build);
    }

    @Test
    void productBuilderNoDescription() {
        DESCRIPTION = null;
        Product.ProductBuilder product = Product.builder()
                .name(NAME)
                .stock(STOCK)
                .price(PRICE);
        assertThrows(IllegalArgumentException.class, product::build);
    }

    @Test
    void productBuilderNoComposition() {
        COMPOSITION = null;
        Product.ProductBuilder product = Product.builder()
                .name(NAME)
                .stock(STOCK)
                .price(PRICE);
        assertThrows(IllegalArgumentException.class, product::build);
    }

    @Test
    void productBuilderNegativeStock() {
        STOCK = -1;
        Product.ProductBuilder product = Product.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .stock(STOCK);
        assertThrows(IllegalArgumentException.class, product::build);
    }

    @Test
    void productBuilderNegativePrice() {
        PRICE = -100000.0;
        Product.ProductBuilder product = Product.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .stock(STOCK)
                .price(PRICE);
        assertThrows(IllegalArgumentException.class, product::build);
    }

    @Test
    void productBuilderNoImageId() {
        Product.ProductBuilder product = Product.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .stock(STOCK)
                .price(PRICE);
        assertThrows(IllegalArgumentException.class, product::build);
    }
}