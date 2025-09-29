package com.tanmay.e_commerce_application.cart_service.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.cart_service.DTO.Request.CartItemRequestDTO;
import com.tanmay.e_commerce_application.cart_service.DTO.Response.CartItemResponseDTO;
import com.tanmay.e_commerce_application.cart_service.DTO.Response.CartResponseDTO;
import com.tanmay.e_commerce_application.cart_service.DTO.Response.VariantResponseDTO;
import com.tanmay.e_commerce_application.cart_service.DTO.Wrapper.ApiResponseWrapper;
import com.tanmay.e_commerce_application.cart_service.Entity.Cart;
import com.tanmay.e_commerce_application.cart_service.Entity.CartItems;
import com.tanmay.e_commerce_application.cart_service.FeignClientInterface.CatalogClient;
import com.tanmay.e_commerce_application.cart_service.Repository.CartItemRepo;
import com.tanmay.e_commerce_application.cart_service.Repository.CartRepo;

@Service
public class CartService {
    @Autowired
    private CartRepo cRepo;

    @Autowired
    private CatalogClient catalogClient;

    @Autowired
    private CartItemRepo cItemRepo;

    public CartResponseDTO getCartItems(String userId) {
        System.out.println(userId);
        return cRepo.findCartWithCartItems(UUID.fromString(userId))
            .map(c -> new CartResponseDTO(c))
            .orElse(new CartResponseDTO());
    }

    public CartItemResponseDTO addCartItem(CartItemRequestDTO cDto, String userId) {
        ResponseEntity<ApiResponseWrapper<VariantResponseDTO>> resp = catalogClient.getVariant(cDto.getVariantId());
        System.out.println(resp);
        if(resp.getStatusCode() != HttpStatus.OK || resp.getBody() == null){
            throw new RuntimeException("Invalid variant");
        }
        VariantResponseDTO vDto = resp.getBody().getPayLoad();
        final Cart cart = cRepo.findByUserId(UUID.fromString(userId))
            .orElseGet(() -> {
                Cart newCart = Cart.builder()
                    .userId(UUID.fromString(userId))
                    .build();
                return cRepo.save(newCart);
            });

        final CartItems cItems = cItemRepo.findByVariantIdAndProductIdAndCartId_Id(UUID.fromString(vDto.getId()), UUID.fromString(vDto.getProductId()),cart.getId())
            .map(ci -> {
                ci.setQuantity(ci.getQuantity() + cDto.getQuantity());
                ci.setPrice(ci.getPrice() + cDto.getQuantity()*vDto.getPriceOverride());
                return cItemRepo.save(ci);
            }).orElseGet(() -> {
                CartItems cartItems = CartItems.builder()
                    .cartId(cart)
                    .productId(UUID.fromString(vDto.getProductId()))
                    .variantId(UUID.fromString(vDto.getId()))
                    .quantity(cDto.getQuantity())
                    .price(vDto.getPriceOverride()*cDto.getQuantity())
                    .build();
                return cItemRepo.save(cartItems);
            });
        return CartItemResponseDTO.builder()
            .id(String.valueOf(cItems.getId()))
            .productId(String.valueOf(cItems.getProductId()))
            .variantId(String.valueOf(cItems.getVariantId()))
            .quantity(cItems.getQuantity())
            .price(cItems.getPrice())
            .build();
    }

}
