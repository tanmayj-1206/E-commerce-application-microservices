package com.tanmay.e_commerce_application.search_service.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanmay.e_commerce_application.search_service.Entity.Product;
import com.tanmay.e_commerce_application.search_service.Service.ProductSearchService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/search")
public class ProductSearchController {

    @Autowired
    private ProductSearchService productSearchService;

    @GetMapping("")
    public List<Product> getProducts() {
        return productSearchService.getProduct();
    }
    
    @PostMapping("/add")
    public String postMethodName(@RequestBody Product product) {
        productSearchService.addProduct(product);
        return "product added";
    }
    
}
