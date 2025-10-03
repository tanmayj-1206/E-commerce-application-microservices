package com.tanmay.e_commerce_application.order_service.DTO.Event;

import java.util.List;
import java.util.UUID;

import com.tanmay.e_commerce_application.order_service.Entity.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEvent {
    private UUID orderId;
    private List<OrderItemEvent> orderItems;

    public static OrderEvent fromEntity(Order order){
        return OrderEvent.builder()
            .orderId(order.getId())
            .orderItems(order.getOrderItems().stream()
                .map(oi -> OrderItemEvent.builder()
                            .id(oi.getId())
                            .variantId(oi.getProductVariantId())
                            .quantity(oi.getQuantity())
                            .build()
                )
                .toList())
            .build();
    }
}
