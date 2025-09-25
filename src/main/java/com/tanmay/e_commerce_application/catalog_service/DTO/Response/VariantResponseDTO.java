package com.tanmay.e_commerce_application.catalog_service.DTO.Response;

import com.tanmay.e_commerce_application.catalog_service.Entity.ProductVariant;
import com.tanmay.e_commerce_application.catalog_service.Enums.Color;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantResponseDTO {
    private String id;
    private String sku;
    private Color color;
    private Double priceOverride;
    private String productId;
    private String productName;

    public static VariantResponseDTO fromEntity(ProductVariant variant){
        return VariantResponseDTO.builder()
            .id(String.valueOf(variant.getId()))
            .color(variant.getColor())
            .sku(variant.getSku())
            .priceOverride(variant.getPriceOverride())
            .productId(String.valueOf(variant.getProductId().getId()))
            .productName(String.valueOf(variant.getProductId().getName()))
            .build();
    }
}
