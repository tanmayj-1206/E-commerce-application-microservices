package com.tanmay.e_commerce_application.catalog_service.Entity;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tanmay.e_commerce_application.catalog_service.DTO.Request.ProductRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category categoryId;

    @OneToMany(mappedBy = "productId")
    @JsonManagedReference
    private List<ProductVariant> variants;

    public static Product toEntity(ProductRequestDTO pDto, Category category){
        return Product.builder()
            .name(pDto.getName())
            .description(pDto.getDescription())
            .brand(pDto.getBrand())
            .categoryId(category)
            .price(pDto.getPrice())
            .build();
    }

    public static Product toEntity(ProductRequestDTO pDto, Category category, UUID id){
        return Product.builder()
            .id(id)
            .name(pDto.getName())
            .description(pDto.getDescription())
            .brand(pDto.getBrand())
            .categoryId(category)
            .price(pDto.getPrice())
            .build();
    }

    public static Product toEntity(ProductRequestDTO pDto, Category category, Product exProduct){
        return Product.builder()
            .id(exProduct.getId())
            .name(pDto.getName() != null ? pDto.getName() : exProduct.getName())
            .description(pDto.getDescription() != null ? pDto.getDescription() : exProduct.getDescription())
            .brand(pDto.getBrand() != null ? pDto.getBrand() : exProduct.getBrand())
            .categoryId(category)
            .price(pDto.getPrice() != null ? pDto.getPrice() : exProduct.getPrice())
            .build();
    }
}
