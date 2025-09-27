package com.tanmay.e_commerce_application.inventory_service.DTO.Response;

import com.tanmay.e_commerce_application.inventory_service.Entity.Inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponseDTO {
    private String id;
    private String variantId;
    private Integer stock;

    public static InventoryResponseDTO fromEntity(Inventory inventory){
        return InventoryResponseDTO.builder()
            .id(String.valueOf(inventory.getId()))
            .variantId(String.valueOf(inventory.getVariantId()))
            .stock(inventory.getStock())
            .build();
    }
}
