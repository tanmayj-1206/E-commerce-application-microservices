package com.tanmay.e_commerce_application.catalog_service.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.catalog_service.DTO.Events.VariantEvent;
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

    @Autowired
    private KafkaTemplate<String, VariantEvent> kafkaTemplate;

    public VariantResponseDTO addVariant(VariantRequestDTO variant) {
        return productRepo.findById(UUID.fromString(variant.getProductId()))
            .map(p -> {
                ProductVariant pVariant = productVariantRepo.save(ProductVariant.toEntity(variant, p));
                kafkaTemplate.send("VARIANT.CREATED", VariantEvent.fromEntity(pVariant));
                return VariantResponseDTO.fromEntity(pVariant);
            })
            .orElseThrow(() -> new RuntimeException("Invalid product id"));
    }

    public VariantResponseDTO getVariant(String id) {
        final ProductVariant pVariant = productVariantRepo.findWithProduct(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("Variant not found"));
        return VariantResponseDTO.fromEntity(pVariant);
    }

}
