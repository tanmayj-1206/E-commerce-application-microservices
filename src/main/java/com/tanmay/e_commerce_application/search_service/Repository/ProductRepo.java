package com.tanmay.e_commerce_application.search_service.Repository;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.tanmay.e_commerce_application.search_service.Entity.Product;


@Repository
public interface ProductRepo extends ElasticsearchRepository<Product, String>{

    Optional<Product> findByProductId(String productId);
}
