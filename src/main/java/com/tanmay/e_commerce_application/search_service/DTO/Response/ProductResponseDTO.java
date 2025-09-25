package com.tanmay.e_commerce_application.search_service.DTO.Response;

import com.tanmay.e_commerce_application.search_service.Entity.Product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponseDTO {

    private String name;
    private String description;
    private String category;
    private Double price;
    private Boolean inStock;

    public static ProductResponseDTO fromEntity(Product product){
        ProductResponseDTO productWrapper = new ProductResponseDTO();
        productWrapper.name = product.getName();
        productWrapper.category = product.getCategory();
        productWrapper.description = product.getDescription();
        productWrapper.price = product.getPrice();
        productWrapper.inStock = product.getInStock();

        return productWrapper;
    }
}
