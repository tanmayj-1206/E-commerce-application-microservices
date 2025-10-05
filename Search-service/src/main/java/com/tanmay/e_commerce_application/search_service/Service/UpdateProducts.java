package com.tanmay.e_commerce_application.search_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.search_service.DTO.Event.ProductEvent;
import com.tanmay.e_commerce_application.search_service.Entity.Product;
import com.tanmay.e_commerce_application.search_service.Repository.ProductRepo;

@Service
public class UpdateProducts {

    @Autowired
    private ProductRepo productRepo;

    @KafkaListener(topics = "PRODUCT.UPDATED", groupId = "ecommerce")
    public void updateProduct(ProductEvent productMap){
        System.out.println(productMap);
        Product product = Product.toEntity(productMap);
        productRepo.findByProductId(product.getProductId())
            .map(p -> {
                product.setId(p.getId());
                return productRepo.save(product);
            })
            .orElseGet(() -> productRepo.save(product));   
    }
}
