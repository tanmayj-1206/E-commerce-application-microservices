package com.tanmay.e_commerce_application.inventory_service.DTO.Events;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemEvent {
    private UUID id;
    private UUID variantId;
    private Integer quantity;
}
