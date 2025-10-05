package com.tanmay.e_commerce_application.catalog_service.DTO.Response;

import com.tanmay.e_commerce_application.catalog_service.Entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
    private String id;
    private String name;
    private String description;
    private String categoryId;
    private String categoryName;
    private String brand;
    private Double price;

    public static ProductResponseDTO fromEntity(Product product){
        return ProductResponseDTO.builder()
            .id(String.valueOf(product.getId()))
            .name(product.getName())
            .description(product.getDescription())
            .categoryId(String.valueOf(product.getCategoryId().getId()))
            .categoryName(product.getCategoryId().getName())
            .brand(product.getBrand())
            .price(product.getPrice())
            .build();
    }
}
