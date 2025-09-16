package com.tanmay.e_commerce_application.catalog_service.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanmay.e_commerce_application.catalog_service.Service.ProductService;
import com.tanmay.e_commerce_application.catalog_service.Wrappers.ApiResponseWrapper;
import com.tanmay.e_commerce_application.catalog_service.Wrappers.ProductWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<ApiResponseWrapper<?>> getProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Products fetched successfully", productService.getProducts(page, size))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<?>> getProduct(@PathVariable String id) {
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Product fetched successfully", productService.getProduct(id))
        );
    }
    

    @PostMapping("/admin")
    public ResponseEntity<ApiResponseWrapper<?>> createProduct(@RequestBody ProductWrapper productWrapper) {
        productService.createProduct(productWrapper);
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Product created successfully", null)
        );
    }
    
    
}
