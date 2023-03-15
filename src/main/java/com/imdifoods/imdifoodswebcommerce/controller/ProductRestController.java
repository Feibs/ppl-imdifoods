package com.imdifoods.imdifoodswebcommerce.controller;

import com.imdifoods.imdifoodswebcommerce.model.Product;
import com.imdifoods.imdifoodswebcommerce.service.CloudinaryService;
import com.imdifoods.imdifoodswebcommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
public class ProductRestController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/add")
    public Product saveProduct(@RequestParam("name") String name,
                               @RequestParam("description") String description,
                               @RequestParam("composition") String composition,
                               @RequestParam("stock") int stock,
                               @RequestParam("price") Double price,
                               @RequestParam("image") MultipartFile imageFile) {
        Product product = null;
        String imageId = cloudinaryService.uploadImage(imageFile);
        if (imageId != null) {
            product = productService.saveProduct(name, description, composition, stock, price, imageId);
        }
        return product;
    }

    @PostMapping(value = "/delete")
    public String deleteProduct(@RequestParam("id") int id) {
        Product product = productService.getProductById(id);
        cloudinaryService.deleteImage(product.getImageId());
        productService.deleteProduct(id);
        return "Product deleted";
    }
}
