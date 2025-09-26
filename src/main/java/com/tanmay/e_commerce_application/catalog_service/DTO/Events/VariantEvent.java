package com.tanmay.e_commerce_application.catalog_service.DTO.Events;

import java.util.UUID;

import com.tanmay.e_commerce_application.catalog_service.Entity.ProductVariant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantEvent {
    private UUID variantId;
    private String sku;

    public static VariantEvent fromEntity(ProductVariant pVariant){
        return VariantEvent.builder()
            .variantId(pVariant.getId())
            .sku(pVariant.getSku())
            .build();
    }
}
