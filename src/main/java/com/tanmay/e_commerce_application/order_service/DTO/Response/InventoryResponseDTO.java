package com.tanmay.e_commerce_application.order_service.DTO.Response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryResponseDTO {
    private String id;
    private String variantId;
    private Integer stock;
}
