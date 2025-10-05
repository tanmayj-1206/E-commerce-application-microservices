package com.tanmay.e_commerce_application.inventory_service.DTO.Events;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VariantEvent {
    private UUID variantId;
    private String sku;
}
