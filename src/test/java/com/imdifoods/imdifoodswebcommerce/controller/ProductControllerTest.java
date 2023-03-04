package com.imdifoods.imdifoodswebcommerce.controller;

import com.imdifoods.imdifoodswebcommerce.model.Product;
import com.imdifoods.imdifoodswebcommerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAddProductTest() throws Exception {
        mvc.perform(get("/product/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void postAddProductTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "image",           // name of the file input field in the form
                "mockImage.jpg",   // original file name
                "multipart/form-data",     // content type of the file
                "mockContent".getBytes() // content of the file
        );

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .multipart("/product/add")
                .file(file)
                .param("name","mockName")
                .param("description","mockDescription")
                .param("stock","1")
                .param("price","2");

        mvc.perform(requestBuilder)
                .andExpect(status().isOk());

        Product product = Product.builder()
                .name("mockName")
                .description("mockDescription")
                .stock(1)
                .price(2)
                .build();

        when(productService.saveProduct(product, file)).thenReturn(product);
        verify(productService).saveProduct(product, file);
    }
}