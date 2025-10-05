package com.tanmay.e_commerce_application.inventory_service.DTO.Response;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemResponseDTO {
    private UUID id;
    private UUID variantId;
    private String name;
    private Integer quantity;
    private Double price;
}
