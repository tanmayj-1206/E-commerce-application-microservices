package com.tanmay.e_commerce_application.cart_service.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemRequestDTO {
    private String productId;
    private String variantId;
    private Integer quantity;
}
