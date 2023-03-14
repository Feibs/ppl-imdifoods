package com.imdifoods.imdifoodswebcommerce.service;

import com.imdifoods.imdifoodswebcommerce.model.Product;
import com.imdifoods.imdifoodswebcommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;

    @Test
    void saveProduct(){
        String name = "mockName";
        String description = "mockDescription";
        int stock = 3;
        Double price = 10000.0;
        String imageId = "imageId";
        Product product = productService.saveProduct(name, description, stock, price, imageId);
        verify(productRepository).save(product);
    }

    @Test
    void saveInvalidProduct(){
        String name = "";
        String description = "";
        int stock = -3;
        Double price = -10000.0;
        String imageId = "";

        assertThrows(IllegalArgumentException.class, () -> productService.saveProduct(name, description, stock, price, imageId));
    }

    @Test
    void testGetAllPageable() {
        int itemCount = 5;

        List<Product> mockProducts = new ArrayList<>();
        for (int i = 0; i< itemCount; i++) {
            mockProducts.add(new Product());
        }

        Page<Product> mockPage = new PageImpl<>(mockProducts);
        when(productRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Page<Product> products = productService.getAllPageable(1, itemCount);
        assertEquals(itemCount, products.getContent().size());
    }

}