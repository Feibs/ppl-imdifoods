package com.imdifoods.imdifoodswebcommerce.controller;

import com.imdifoods.imdifoodswebcommerce.utils.PageMaker;
import com.imdifoods.imdifoodswebcommerce.model.Product;
import com.imdifoods.imdifoodswebcommerce.service.CloudinaryService;
import com.imdifoods.imdifoodswebcommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CloudinaryService cloudinaryService;

    private static final int ITEM_COUNT = 10;

    @GetMapping("")
    public String getAdminProductsPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                       Model model) {
        if (page < 1) {
            return "redirect:/product?page=1";
        }

        Page<Product> products = productService.getAllPageable(page, ITEM_COUNT);
        if (products.getTotalPages() < page) {
            return "redirect:/product?page=" + products.getTotalPages();
        }

        PageMaker pageMaker = new PageMaker(products.getTotalPages(), products.getNumber(), 5);

        model.addAttribute("products", products);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("countStart", products.getNumber()*ITEM_COUNT);

        return "products";
    }

    @GetMapping("/add")
    public String getAddProduct() {
        return "createProduct";
    }
}
