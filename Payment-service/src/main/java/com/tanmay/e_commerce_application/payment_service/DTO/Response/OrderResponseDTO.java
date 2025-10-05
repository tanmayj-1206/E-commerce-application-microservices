package com.tanmay.e_commerce_application.payment_service.DTO.Response;

import java.util.List;
import java.util.UUID;

import com.tanmay.e_commerce_application.payment_service.Enums.OrderStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderResponseDTO {
    private UUID id;
    private UUID userId;
    private List<OrderItemResponseDTO> orderItems;
    private Double amount;
    private OrderStatus status;
}
