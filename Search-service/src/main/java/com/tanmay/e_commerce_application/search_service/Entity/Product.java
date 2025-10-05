package com.tanmay.e_commerce_application.search_service.Entity;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.suggest.Completion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tanmay.e_commerce_application.search_service.DTO.Event.ProductEvent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "products")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String productId;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Keyword)
    private String brand;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Boolean)
    private Boolean inStock;

    @CompletionField
    private Completion suggestion;

    public static Product toEntity(ProductEvent pEvent){
        return Product.builder()
            .category(pEvent.getCategory())
            .description(pEvent.getDescription())
            .name(pEvent.getName())
            .price(pEvent.getPrice())
            .productId(pEvent.getId())
            .brand(pEvent.getBrand())
            .suggestion(new Completion(List.of(pEvent.getName(), pEvent.getCategory(), pEvent.getBrand())))
            .build();

    }
}
