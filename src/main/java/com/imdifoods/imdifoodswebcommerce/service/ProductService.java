package com.imdifoods.imdifoodswebcommerce.service;

import com.imdifoods.imdifoodswebcommerce.model.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
    Product saveProduct(String name, String description, String composition, int stock, Double price, String imageId);

    void deleteProduct(int id);

    Product getProductById(int id);

    Page<Product> getAllPageable(int page, int itemPerPage);
}
