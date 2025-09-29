package com.tanmay.e_commerce_application.catalog_service.Controller;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanmay.e_commerce_application.catalog_service.DTO.Request.VariantRequestDTO;
import com.tanmay.e_commerce_application.catalog_service.DTO.Wrappers.ApiResponseWrapper;
import com.tanmay.e_commerce_application.catalog_service.Service.ProductVariantService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("api/variant")
public class ProductVariantsController {
    @Autowired
    private ProductVariantService productVariantService;

    @PostMapping("add")
    public ResponseEntity<ApiResponseWrapper<?>> addVariant(@RequestBody VariantRequestDTO variant) {        
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Variant added successfully", productVariantService.addVariant(variant))
        );
    }
    
    @GetMapping("get/{id}")
    public ResponseEntity<ApiResponseWrapper<?>> getVariant(@PathVariable String id) {
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Variant fetched successfully", productVariantService.getVariant(id))
        );
    }

    @PostMapping("getvariants")
    public ResponseEntity<ApiResponseWrapper<?>> getVariants(@RequestBody Set<UUID> idList) {
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Variants fetched", productVariantService.getVariants(idList))
        );
    }
    
    
}   
