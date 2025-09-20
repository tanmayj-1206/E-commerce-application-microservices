package com.tanmay.e_commerce_application.search_service.Entity;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.suggest.Completion;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(indexName = "products")
public class Product {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Boolean)
    private Boolean inStock;

    @CompletionField
    private Completion suggestion;

    public Product(Map<String, Object> productMap){
        this.name = (String) productMap.get("name");
        this.description = (String) productMap.get("description");
        this.category = (String) productMap.get("category");
        this.price = (Double) productMap.get("price");
        this.inStock = (Boolean) productMap.get("inStock");
        this.suggestion = new Completion(List.of(
            new String[]{this.name}
        ));
    }
}
