package com.tanmay.e_commerce_application.catalog_service.Wrappers;

import com.tanmay.e_commerce_application.catalog_service.Entity.Product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductWrapper {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String sku;
    private String categoryName;
    private String categoryId;

    public ProductWrapper(Long id, String name, String description, Double price, Integer quantity, String sku, String categoryName, Long categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.sku = sku;
        this.categoryName = categoryName;
        this.categoryId = String.valueOf(categoryId);
    }

    public static ProductWrapper fromEntity(Product product) {
        return new ProductWrapper(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getQuantity(),
            product.getSku(),
            product.getCategory() != null ? product.getCategory().getName() : null,
            product.getCategory() != null ? product.getCategory().getId() : null
        );
    }
}
