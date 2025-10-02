package com.tanmay.e_commerce_application.order_service.DTO.Response;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.tanmay.e_commerce_application.order_service.Entity.Order;
import com.tanmay.e_commerce_application.order_service.Enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private UUID id;
    private UUID userId;
    private List<OrderItemResponseDTO> orderItems;
    private Double amount;
    private Status status;

    public static OrderResponseDTO fromEntity(Order order, Map<String, VariantResponseDTO> mapIdVsVariant){
        return OrderResponseDTO.builder()
            .id(order.getId())
            .userId(order.getUserId())
            .amount(order.getAmount())
            .status(order.getOrderStatus())
            .orderItems(order.getOrderItems().stream()
                .map(oi -> OrderItemResponseDTO.fromEntity(oi, mapIdVsVariant.get(String.valueOf(oi.getProductVariantId())).getProductName()))
                .toList())
            .build();
    }
}
