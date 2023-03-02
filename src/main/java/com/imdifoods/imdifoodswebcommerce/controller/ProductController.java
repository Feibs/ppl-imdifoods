package com.imdifoods.imdifoodswebcommerce.controller;

import com.imdifoods.imdifoodswebcommerce.model.Product;
import com.imdifoods.imdifoodswebcommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/add")
    public String getAddProduct() {
        return "createProduct";
    }

    @PostMapping("/add")
    public String saveProduct(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("stock") int stock,
                              @RequestParam("price") int price,
                              @RequestParam("image") MultipartFile imageFile) throws IOException {
        Product product = Product.builder()
                .name(name)
                .description(description)
                .stock(stock)
                .price(price)
                .build();
        productService.saveProduct(product,imageFile);
        return "createProduct";
    }
}
