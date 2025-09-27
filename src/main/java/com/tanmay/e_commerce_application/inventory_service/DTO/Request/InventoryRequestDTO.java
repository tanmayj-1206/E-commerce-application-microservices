package com.tanmay.e_commerce_application.inventory_service.DTO.Request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryRequestDTO {
    private String variantId;
    private Integer newStock;
}
