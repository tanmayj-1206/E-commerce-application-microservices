package com.tanmay.e_commerce_application.search_service.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.search_service.Entity.Product;
import com.tanmay.e_commerce_application.search_service.Repository.ProductRepo;

@Service
public class ProductSearchService {
    
    @Autowired
    private ProductRepo productRepo;

    public void addProduct(Product product){
        productRepo.save(product);
    }

    public List<Product> getProduct(){
        Iterable<Product> iterable = productRepo.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                            .collect(Collectors.toList());
    }
}
