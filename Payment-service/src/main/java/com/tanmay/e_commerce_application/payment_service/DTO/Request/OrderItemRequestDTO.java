package com.tanmay.e_commerce_application.payment_service.DTO.Request;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemRequestDTO {
    private UUID variantId;
    private Integer quantity;
}
