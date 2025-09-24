package com.tanmay.e_commerce_application.catalog_service.Entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tanmay.e_commerce_application.catalog_service.DTO.Request.VariantRequestDTO;
import com.tanmay.e_commerce_application.catalog_service.Enums.Color;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product productId;

    private String sku;

    @Enumerated(EnumType.STRING)
    private Color color;

    private Integer priceOverride;

    public static ProductVariant toEntity(VariantRequestDTO vDto, Product product){
        return ProductVariant.builder()
            .color(vDto.getColor())
            .priceOverride(vDto.getPrice())
            .productId(product)
            .sku(vDto.getSku())
            .build();
    }
}
