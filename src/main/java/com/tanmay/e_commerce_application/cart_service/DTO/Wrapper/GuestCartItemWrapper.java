package com.tanmay.e_commerce_application.cart_service.DTO.Wrapper;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuestCartItemWrapper {
    private UUID productId;
    private UUID variantId;
    private Integer quantity;
    private Double price;
}
