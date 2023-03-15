package com.imdifoods.imdifoodswebcommerce.controller;

import com.imdifoods.imdifoodswebcommerce.exception.NonPositivePageableException;
import com.imdifoods.imdifoodswebcommerce.exception.OverflowPageableException;
import com.imdifoods.imdifoodswebcommerce.model.Product;
import com.imdifoods.imdifoodswebcommerce.service.CloudinaryService;
import com.imdifoods.imdifoodswebcommerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    void getAddProductTest() throws Exception {
        mvc.perform(get("/product/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetAdminProductsPage() throws Exception {
        List<Product> mockProductList = new ArrayList<>();
        mockProductList.add(new Product());

        Page<Product> mockProduct = new PageImpl<>(mockProductList);
        when(productService.getAllPageable(anyInt(), anyInt())).thenReturn(mockProduct);

        mvc.perform(get("/product"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetAdminProductPageLessThanOnePageRedirectToFirstPage() throws Exception {
        when(productService.getAllPageable(anyInt(), anyInt())).thenThrow(new NonPositivePageableException("-"));

        mvc.perform(get("/product?page=0"))
                .andExpect(redirectedUrl("/product?page=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser
    void testGetAdminProductPageOverflowPageRedirectToLastPage() throws Exception {
        String validPage = "3";
        when(productService.getAllPageable(anyInt(), anyInt())).thenThrow(new OverflowPageableException("-", validPage));

        mvc.perform(get("/product?page=100"))
                .andExpect(redirectedUrl("/product?page=" + validPage))
                .andExpect(status().is3xxRedirection())
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser
    void testGetAdminProductPageOnPageZeroNoProduct() throws Exception {
        Page<Product> mockProduct = new PageImpl<>(new ArrayList<>());
        when(productService.getAllPageable(anyInt(),anyInt())).thenReturn(mockProduct);

        mvc.perform(get("/product?page=0"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getUpdateProductTest() throws Exception {
        mvc.perform(get("/product/update"))
                .andExpect(status().isOk());
    }
}