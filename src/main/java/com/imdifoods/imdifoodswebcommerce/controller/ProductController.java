package com.imdifoods.imdifoodswebcommerce.controller;

import com.imdifoods.imdifoodswebcommerce.exception.NonPositivePageableException;
import com.imdifoods.imdifoodswebcommerce.exception.OverflowPageableException;
import com.imdifoods.imdifoodswebcommerce.model.Product;
import com.imdifoods.imdifoodswebcommerce.service.ProductService;
import com.imdifoods.imdifoodswebcommerce.utils.PageMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/product")
public class ProductController {
    private static final int ITEM_COUNT = 10;
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String getAdminProductsPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                       Model model) {
        try {
            Page<Product> products = productService.getAllPageable(page, ITEM_COUNT);
            var pageMaker = new PageMaker(products.getTotalPages(), products.getNumber(), 5);

            model.addAttribute("products", products);
            model.addAttribute("pageMaker", pageMaker);
            model.addAttribute("countStart", products.getNumber() * ITEM_COUNT);
            model.addAttribute("currentPage", page);

            return "products";
        } catch (NonPositivePageableException e) {
            return "redirect:/product?page=1";
        } catch (OverflowPageableException e) {
            return "redirect:/product?page=" + e.getValidPage();
        }
    }

    @GetMapping("/add")
    public String getAddProduct() {
        return "create-product";
    }

    @GetMapping("/update")
    public String getUpdateProduct() {
        return "update-product";
    }
}
