package com.tanmay.e_commerce_application.catalog_service.DTO.Request;

import com.tanmay.e_commerce_application.catalog_service.Enums.Color;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VariantRequestDTO {
    private String productId;
    private Color color;
    private Double price;
    private String sku;
}
