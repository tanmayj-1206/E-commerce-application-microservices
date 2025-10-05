package com.tanmay.e_commerce_application.cart_service.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponseDTO {
    private String id;
    private String variantId;
    private String productId;
    private Integer quantity;
    private Double price;
}
