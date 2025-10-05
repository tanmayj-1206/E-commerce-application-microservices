package com.tanmay.e_commerce_application.order_service.DTO.Response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VariantResponseDTO {
    private String id;
    private String sku;
    private Double priceOverride;
    private String productId;
    private String productName;
}
