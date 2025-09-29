package com.tanmay.e_commerce_application.cart_service.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantResponseDTO {
    private String id;
    private String sku;
    private Double priceOverride;
    private String productId;
    private String productName;
}
