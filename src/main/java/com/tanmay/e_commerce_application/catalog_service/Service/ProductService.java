package com.tanmay.e_commerce_application.catalog_service.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.catalog_service.Entity.Category;
import com.tanmay.e_commerce_application.catalog_service.Entity.Product;
import com.tanmay.e_commerce_application.catalog_service.Repository.CategoryRepo;
import com.tanmay.e_commerce_application.catalog_service.Repository.ProductRepository;
import com.tanmay.e_commerce_application.catalog_service.Wrappers.ProductWrapper;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private KafkaTemplate<String, Map<String, Object>> kafkaTemplate;

    public Page<ProductWrapper> getProducts(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ProductWrapper> productsPage = productRepository.findAll(pageable)
                                                        .map(ProductWrapper::fromEntity);
        return productsPage;
    }

    public void createProduct(ProductWrapper productWrapper) {
        Product product = new Product();
        product.setName(productWrapper.getName());
        product.setDescription(productWrapper.getDescription());
        product.setPrice(productWrapper.getPrice());
        product.setQuantity(productWrapper.getQuantity());
        product.setSku(productWrapper.getSku());
        Category category = categoryRepo.findById(Long.parseLong(productWrapper.getCategoryId())).get();
        System.out.println(category);
        publishProduct(productWrapper, category.getName());
        product.setCategory(category);
        productRepository.save(product);
    }

    public ProductWrapper getProduct(String id) {
        return productRepository.findById(Long.valueOf(id))
                                .map(ProductWrapper::fromEntity)
                                .orElse(new ProductWrapper());
    }

    private void publishProduct(ProductWrapper productWrapper, String categoryName){
        Map<String, Object> proMap = new HashMap<>();
        proMap.put("name", productWrapper.getName());
        proMap.put("description", productWrapper.getDescription());
        proMap.put("price", productWrapper.getPrice());
        proMap.put("inStock", productWrapper.getQuantity() > 0 ? true : false);
        proMap.put("category", categoryName);
        kafkaTemplate.send("products", proMap);
    }

}
