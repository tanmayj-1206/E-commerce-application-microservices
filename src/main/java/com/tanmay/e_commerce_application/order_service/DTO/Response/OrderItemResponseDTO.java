package com.tanmay.e_commerce_application.order_service.DTO.Response;

import java.util.UUID;

import com.tanmay.e_commerce_application.order_service.Entity.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDTO {
    private UUID id;
    private UUID variantId;
    private Integer quantity;
    private Double price;

    public static OrderItemResponseDTO fromEntity(OrderItem orderItem){
        return OrderItemResponseDTO.builder()
            .id(orderItem.getId())
            .price(orderItem.getPrice())
            .variantId(orderItem.getProductVariantId())
            .quantity(orderItem.getQuantity())
            .build();
    }
}
