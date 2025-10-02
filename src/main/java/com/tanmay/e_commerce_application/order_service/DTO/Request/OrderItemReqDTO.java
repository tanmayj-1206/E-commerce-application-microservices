package com.tanmay.e_commerce_application.order_service.DTO.Request;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemReqDTO {
    private UUID variantId;
    private Integer quantity;
}
