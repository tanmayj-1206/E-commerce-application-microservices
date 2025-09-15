package com.tanmay.e_commerce_application.catalog_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.catalog_service.Entity.Category;
import com.tanmay.e_commerce_application.catalog_service.Entity.Product;
import com.tanmay.e_commerce_application.catalog_service.Repository.ProductRepository;
import com.tanmay.e_commerce_application.catalog_service.Wrappers.ProductWrapper;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getProducts(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Product> productsPage = productRepository.findAll(pageable);
        return productsPage;
    }

    public void createProduct(ProductWrapper productWrapper) {
        Product product = new Product();
        product.setName(productWrapper.getName());
        product.setDescription(productWrapper.getDescription());
        product.setPrice(productWrapper.getPrice());
        product.setQuantity(productWrapper.getQuantity());
        product.setSku(productWrapper.getSku());
        Category category = new Category();
        category.setId(Long.parseLong(productWrapper.getCategoryId()));
        product.setCategory(category);
        productRepository.save(product);
    }

}
