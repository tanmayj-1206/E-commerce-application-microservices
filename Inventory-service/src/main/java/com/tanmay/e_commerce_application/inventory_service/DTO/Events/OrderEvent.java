package com.tanmay.e_commerce_application.inventory_service.DTO.Events;

import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderEvent {
    private UUID orderId;
    private List<OrderItemEvent> orderItems;
}
