package com.imdifoods.imdifoodswebcommerce.service;

import com.imdifoods.imdifoodswebcommerce.exception.NonPositivePageableException;
import com.imdifoods.imdifoodswebcommerce.exception.OverflowPageableException;
import com.imdifoods.imdifoodswebcommerce.exception.ProductNotFoundException;
import com.imdifoods.imdifoodswebcommerce.model.Product;
import com.imdifoods.imdifoodswebcommerce.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;

    @Test
    void saveProduct() {
        String name = "mockName";
        String description = "mockDescription";
        String composition = "mockComposition";
        int stock = 3;
        Double price = 10000.0;
        String imageId = "imageId";
        Product product = productService.saveProduct(name, description, composition, stock, price, imageId);
        verify(productRepository).save(product);
    }

    @Test
    void saveInvalidProduct() {
        String name = "";
        String description = "";
        String composition = "";
        int stock = -3;
        Double price = -10000.0;
        String imageId = "";

        assertThrows(IllegalArgumentException.class, () -> productService.saveProduct(name, description, composition, stock, price, imageId));
    }

    @Test
    void deleteProductById() {
        Product product = mock(Product.class);
        int id = product.getId();
        productRepository.save(product);
        productService.deleteProduct(id);
        verify(productRepository).deleteById(id);
    }

    @Test
    void getProductByIdReturnsProduct() {
        Optional<Product> product = mock(Optional.class);
        when(productRepository.findById(anyInt())).thenReturn(product);
        when(product.isPresent()).thenReturn(true);
        productService.getProductById(anyInt());
        verify(product).isPresent();
        verify(product).get();
    }

    @Test
    void getProductByIdThrowsNotFoundException() {
        int id = anyInt();
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(id));
    }

    @Test
    void testGetAllPageable() {
        int itemCount = 5;

        List<Product> mockProducts = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            mockProducts.add(new Product());
        }

        Page<Product> mockPage = new PageImpl<>(mockProducts);
        when(productRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Page<Product> products = productService.getAllPageable(1, itemCount);
        assertEquals(itemCount, products.getContent().size());
    }

    @Test
    void testGetAllPageableNegativePage() {
        int itemCount = 5;
        int page = -1;

        assertThrows(NonPositivePageableException.class, () -> productService.getAllPageable(page, itemCount));
    }

    @Test
    void testGetAllPageableOverflowPage() {
        int itemCount = 5;
        int page = 2;

        List<Product> mockProducts = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            mockProducts.add(new Product());
        }

        Page<Product> mockPage = new PageImpl<>(mockProducts);
        when(productRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        assertThrows(OverflowPageableException.class, () -> productService.getAllPageable(page, itemCount));
    }

    @Test
    void testGetAllPageableZeroItem() {
        int itemCount = 5;
        int page = 1;
        when(productRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        Page<Product> pages = productService.getAllPageable(page, itemCount);
        assertEquals(0, pages.getContent().size());
    }
}