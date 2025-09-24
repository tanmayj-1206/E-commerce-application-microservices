package com.tanmay.e_commerce_application.catalog_service.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.catalog_service.DTO.Request.VariantRequestDTO;
import com.tanmay.e_commerce_application.catalog_service.DTO.Response.VariantResponseDTO;
import com.tanmay.e_commerce_application.catalog_service.Entity.ProductVariant;
import com.tanmay.e_commerce_application.catalog_service.Repository.ProductRepo;
import com.tanmay.e_commerce_application.catalog_service.Repository.ProductVariantRepo;

@Service
public class ProductVariantService {
    @Autowired
    private ProductVariantRepo productVariantRepo;

    @Autowired
    private ProductRepo productRepo;

    public VariantResponseDTO addVariant(VariantRequestDTO variant) {
        return productRepo.findById(UUID.fromString(variant.getProductId()))
            .map(p -> VariantResponseDTO.fromEntity(productVariantRepo.save(ProductVariant.toEntity(variant, p))))
            .orElseThrow(() -> new RuntimeException("Invalid product id"));
    }

}
