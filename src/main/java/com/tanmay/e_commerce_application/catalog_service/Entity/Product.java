package com.tanmay.e_commerce_application.catalog_service.Entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tanmay.e_commerce_application.catalog_service.DTO.Request.ProductRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    private String brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category categoryId;

    public static Product toEntity(ProductRequestDTO pDto, Category category){
        return Product.builder()
            .id(pDto.getId() != null ? UUID.fromString(pDto.getId()) : null)
            .name(pDto.getName())
            .description(pDto.getDescription())
            .brand(pDto.getBrand())
            .categoryId(category)
            .build();
    }
}
