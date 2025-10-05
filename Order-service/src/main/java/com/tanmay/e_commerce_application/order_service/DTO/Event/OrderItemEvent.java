package com.tanmay.e_commerce_application.order_service.DTO.Event;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemEvent {
    private UUID id;
    private UUID variantId;
    private Integer quantity;
}
