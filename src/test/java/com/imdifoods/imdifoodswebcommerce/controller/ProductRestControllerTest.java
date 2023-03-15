package com.imdifoods.imdifoodswebcommerce.controller;

import com.imdifoods.imdifoodswebcommerce.exception.ProductNotFoundException;
import com.imdifoods.imdifoodswebcommerce.model.Product;
import com.imdifoods.imdifoodswebcommerce.service.CloudinaryService;
import com.imdifoods.imdifoodswebcommerce.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {
    private final int ID = 1;
    private final String NAME = "mockName";
    private final String DESCRIPTION = "mockDescription";
    private final String COMPOSITION = "mockComposition";
    private final int STOCK = 3;
    private final Double PRICE = 10000.0;
    private final String IMAGEID = "imageId";
    private final MockMultipartFile FILE = new MockMultipartFile(
            "image",           // name of the file input field in the form
            "mockImage.jpg",   // original file name
            "multipart/form-data",     // content type of the file
            "mockContent".getBytes() // content of the file
    );
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProductService productService;
    @MockBean
    private CloudinaryService cloudinaryService;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenImageUploadSuccess_ShouldSaveProduct() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .multipart("/product/add")
                .file(FILE)
                .with(csrf())
                .param("name", NAME)
                .param("description", DESCRIPTION)
                .param("composition", COMPOSITION)
                .param("stock", String.valueOf(STOCK))
                .param("price", String.valueOf(PRICE));

        when(cloudinaryService.uploadImage(FILE)).thenReturn(IMAGEID);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(cloudinaryService).uploadImage(FILE);
        verify(productService).saveProduct(NAME, DESCRIPTION, COMPOSITION, STOCK, PRICE, IMAGEID);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void whenDeleteProduct_ShouldReturnOk() throws Exception {
        Product product = mock(Product.class);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .multipart("/product/delete")
                .with(csrf())
                .param("id", String.valueOf(ID));

        when(productService.getProductById(anyInt())).thenReturn(product);
        when(product.getImageId()).thenReturn(IMAGEID);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(cloudinaryService).deleteImage(IMAGEID);
        verify(productService).deleteProduct(ID);
    }

    @Test
    void whenDeleteNullProduct_ShouldThrowException() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .multipart("/product/delete")
                .param("id", String.valueOf(ID));

        when(productService.getProductById(anyInt())).thenThrow(new ProductNotFoundException(""));
        mvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError());
    }
}