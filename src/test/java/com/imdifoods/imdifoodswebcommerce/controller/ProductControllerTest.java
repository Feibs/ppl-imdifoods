package com.imdifoods.imdifoodswebcommerce.controller;

import com.imdifoods.imdifoodswebcommerce.service.CloudinaryService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private CloudinaryService cloudinaryService;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAddProductTest() throws Exception {
        mvc.perform(get("/product/add"))
                .andExpect(status().isOk());
    }

    @Test
    void whenImageUploadFail_ShouldDisplayPreviousData() throws Exception {
        String name = "mockName";
        String description = "mockDescription";
        int stock = 3;
        Double price = 10000.0;

        MockMultipartFile file = new MockMultipartFile(
                "image",           // name of the file input field in the form
                "mockImage.jpg",   // original file name
                "multipart/form-data",     // content type of the file
                "mockContent".getBytes() // content of the file
        );

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .multipart("/product/add")
                .file(file)
                .param("name",name)
                .param("description",description)
                .param("stock",String.valueOf(stock))
                .param("price",String.valueOf(price));

        mvc.perform(requestBuilder)
                .andExpect(status().isOk());
        verify(cloudinaryService).uploadImage(file);
    }

    @Test
    void whenImageUploadSuccess_ShouldSaveProduct() throws Exception {
        String name = "mockName";
        String description = "mockDescription";
        int stock = 3;
        Double price = 10000.0;
        String imageId = "imageId";

        MockMultipartFile file = new MockMultipartFile(
                "image",           // name of the file input field in the form
                "mockImage.jpg",   // original file name
                "multipart/form-data",     // content type of the file
                "mockContent".getBytes() // content of the file
        );

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .multipart("/product/add")
                .file(file)
                .param("name",name)
                .param("description",description)
                .param("stock",String.valueOf(stock))
                .param("price",String.valueOf(price));

        when(cloudinaryService.uploadImage(file)).thenReturn(imageId);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(cloudinaryService).uploadImage(file);
        verify(cloudinaryService).getImageUrl(imageId);
        verify(productService).saveProduct(anyString(),anyString(),anyInt(),anyDouble(),anyString());
    }
}