package com.tanmay.e_commerce_application.search_service.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(indexName = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String sku;
}
