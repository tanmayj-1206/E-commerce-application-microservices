package com.tanmay.e_commerce_application.catalog_service.DTO.Events;

import com.tanmay.e_commerce_application.catalog_service.Entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEvent {
    private String id;
    private String name;
    private String description;
    private String category;
    private String brand;
    private Double price;

    public static ProductEvent fromEntity(Product product){
        return ProductEvent.builder()
            .id(String.valueOf(product.getId()))
            .name(product.getName())
            .description(product.getDescription())
            .brand(product.getBrand())
            .category(product.getCategoryId().getName())
            .price(product.getPrice())
            .build();
    }
}
