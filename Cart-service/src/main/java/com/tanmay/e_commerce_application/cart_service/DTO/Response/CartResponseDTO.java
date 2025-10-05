package com.tanmay.e_commerce_application.cart_service.DTO.Response;

import java.util.List;

import com.tanmay.e_commerce_application.cart_service.Entity.Cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDTO {
    private String cartId;
    private String userId;
    private List<CartItemResponseDTO> cartItems;

    public CartResponseDTO(Cart cart){
        this.cartId = String.valueOf(cart.getId());
        this.userId = String.valueOf(cart.getUserId());
        this.cartItems = cart.getCartItems().stream()
                .map(ci -> CartItemResponseDTO.builder()
                    .id(String.valueOf(ci.getId()))
                    .productId(String.valueOf(ci.getProductId()))
                    .variantId(String.valueOf(ci.getVariantId()))
                    .quantity(ci.getQuantity())
                    .price(ci.getPrice())
                    .build()
                )
                .toList();
    }
}
