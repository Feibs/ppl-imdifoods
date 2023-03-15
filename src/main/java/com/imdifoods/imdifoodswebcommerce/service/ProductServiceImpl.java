package com.imdifoods.imdifoodswebcommerce.service;

import com.imdifoods.imdifoodswebcommerce.exception.NonPositivePageableException;
import com.imdifoods.imdifoodswebcommerce.exception.OverflowPageableException;
import com.imdifoods.imdifoodswebcommerce.exception.ProductNotFoundException;
import com.imdifoods.imdifoodswebcommerce.model.Product;
import com.imdifoods.imdifoodswebcommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(String name, String description, String composition, int stock, Double price, String imageId) {
        var product = Product.builder()
                .name(name)
                .description(description)
                .composition(composition)
                .stock(stock)
                .price(price)
                .imageId(imageId)
                .build();
        productRepository.save(product);
        return product;
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(int id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
    }

    @Override
    public Page<Product> getAllPageable(int page, int itemPerPage) {
        if (page < 1) throw new NonPositivePageableException("Page must be greater than 0");

        Pageable pageable = PageRequest.of(page - 1, itemPerPage);
        var pages = productRepository.findAll(pageable);

        if (pages.getTotalPages() != 0 && pages.getTotalPages() < page) {
            throw new OverflowPageableException(
                    "Page must be less than " + pages.getTotalPages(),
                    String.valueOf(pages.getTotalPages())
            );
        }

        return pages;
    }
}
