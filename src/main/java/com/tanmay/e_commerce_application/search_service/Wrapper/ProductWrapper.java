package com.tanmay.e_commerce_application.search_service.Wrapper;

import com.tanmay.e_commerce_application.search_service.Entity.Product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductWrapper {

    private String name;
    private String description;
    private String category;
    private Double price;
    private Boolean inStock;

    public static ProductWrapper fromEntity(Product product){
        ProductWrapper productWrapper = new ProductWrapper();
        productWrapper.name = product.getName();
        productWrapper.category = product.getCategory();
        productWrapper.description = product.getDescription();
        productWrapper.price = product.getPrice();
        productWrapper.inStock = product.getInStock();

        return productWrapper;
    }
}
