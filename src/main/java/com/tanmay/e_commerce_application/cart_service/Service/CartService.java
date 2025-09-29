package com.tanmay.e_commerce_application.cart_service.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.cart_service.DTO.Request.CartItemRequestDTO;
import com.tanmay.e_commerce_application.cart_service.DTO.Response.CartItemResponseDTO;
import com.tanmay.e_commerce_application.cart_service.DTO.Response.CartResponseDTO;
import com.tanmay.e_commerce_application.cart_service.DTO.Response.GuestCartDTO;
import com.tanmay.e_commerce_application.cart_service.DTO.Response.InventoryResponseDTO;
import com.tanmay.e_commerce_application.cart_service.DTO.Response.VariantResponseDTO;
import com.tanmay.e_commerce_application.cart_service.DTO.Wrapper.ApiResponseWrapper;
import com.tanmay.e_commerce_application.cart_service.DTO.Wrapper.GuestCartItemWrapper;
import com.tanmay.e_commerce_application.cart_service.DTO.Wrapper.GuestCartWrapper;
import com.tanmay.e_commerce_application.cart_service.Entity.Cart;
import com.tanmay.e_commerce_application.cart_service.Entity.CartItems;
import com.tanmay.e_commerce_application.cart_service.FeignClientInterface.CatalogClient;
import com.tanmay.e_commerce_application.cart_service.FeignClientInterface.InventoryClient;
import com.tanmay.e_commerce_application.cart_service.Repository.CartItemRepo;
import com.tanmay.e_commerce_application.cart_service.Repository.CartRepo;

@Service
public class CartService {
    @Autowired
    private CartRepo cRepo;

    @Autowired
    private CatalogClient catalogClient;

    @Autowired
    private InventoryClient iClient;

    @Autowired
    private CartItemRepo cItemRepo;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public CartResponseDTO getCartItems(String userId) {
        System.out.println(userId);
        return cRepo.findCartWithCartItems(UUID.fromString(userId))
            .map(c -> new CartResponseDTO(c))
            .orElse(new CartResponseDTO());
    }

    public CartItemResponseDTO addCartItem(CartItemRequestDTO cDto, String userId) {
        ResponseEntity<ApiResponseWrapper<VariantResponseDTO>> resp = catalogClient.getVariant(cDto.getVariantId());
        ResponseEntity<ApiResponseWrapper<InventoryResponseDTO>> response = iClient.getInventory(cDto.getVariantId());

        if(resp.getStatusCode() != HttpStatus.OK || resp.getBody() == null || response.getStatusCode() != HttpStatus.OK || response.getBody() == null){
            throw new RuntimeException("Invalid variant");
        }
        InventoryResponseDTO iDto = response.getBody().getPayLoad();
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
                if(ci.getQuantity() + cDto.getQuantity() > iDto.getStock()){
                    throw new RuntimeException("Unsufficient stock");
                }
                ci.setQuantity(ci.getQuantity() + cDto.getQuantity());
                ci.setPrice(ci.getPrice() + cDto.getQuantity()*vDto.getPriceOverride());
                return cItemRepo.save(ci);
            }).orElseGet(() -> {
                if(cDto.getQuantity() > iDto.getStock()){
                    throw new RuntimeException("Unsufficient stock");
                }
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

    public GuestCartDTO createGuestCart() {
        String guestCartId = String.valueOf(UUID.randomUUID());
        GuestCartWrapper gWrapper = GuestCartWrapper.builder()
            .guestCartId(UUID.fromString(guestCartId))
            .guestCartItems(new HashMap<>())
            .build();

        redisTemplate.opsForValue().set(getGuestUserKey(guestCartId), gWrapper);

        return GuestCartDTO.builder()
            .guestCartId(guestCartId)
            .build();
    }

    public GuestCartDTO addItemToGuestCart(CartItemRequestDTO cDto, String guestCartId){
        ResponseEntity<ApiResponseWrapper<VariantResponseDTO>> resp = catalogClient.getVariant(cDto.getVariantId());
        ResponseEntity<ApiResponseWrapper<InventoryResponseDTO>> response = iClient.getInventory(cDto.getVariantId());
        
        if(resp.getStatusCode() != HttpStatus.OK || resp.getBody() == null || response.getStatusCode() != HttpStatus.OK || response.getBody() == null){
            throw new RuntimeException("Invalid variant");
        }
        InventoryResponseDTO iDto = response.getBody().getPayLoad();
        VariantResponseDTO vDto = resp.getBody().getPayLoad();
        GuestCartWrapper gWrapper = (GuestCartWrapper) redisTemplate.opsForValue().get(getGuestUserKey(guestCartId));

        if(gWrapper == null){
            gWrapper = GuestCartWrapper.builder()
                .guestCartId(UUID.fromString(guestCartId))
                .guestCartItems(new HashMap<>())
                .build();
        }

        Map<UUID, GuestCartItemWrapper> mapIdVsCartItem = gWrapper.getGuestCartItems();
        GuestCartItemWrapper gItemWrapper = mapIdVsCartItem.get(UUID.fromString(vDto.getId()));

        if(gItemWrapper == null){
            if(cDto.getQuantity() > iDto.getStock()){
                throw new RuntimeException("Unsufficient stock");
            }
            gItemWrapper = GuestCartItemWrapper.builder()
                .variantId(UUID.fromString(vDto.getId()))
                .productId(UUID.fromString(vDto.getProductId()))
                .quantity(cDto.getQuantity())
                .price(cDto.getQuantity() * vDto.getPriceOverride())
                .build();
        } else {
            if(gItemWrapper.getQuantity() + cDto.getQuantity() > iDto.getStock()){
                throw new RuntimeException("Unsufficient stock");
            }
            gItemWrapper.setQuantity(gItemWrapper.getQuantity() + cDto.getQuantity());
            gItemWrapper.setPrice(gItemWrapper.getPrice() + cDto.getQuantity() * vDto.getPriceOverride());
        }

        mapIdVsCartItem.put(UUID.fromString(vDto.getId()), gItemWrapper);
        gWrapper.setGuestCartItems(mapIdVsCartItem);
        redisTemplate.opsForValue().set(getGuestUserKey(guestCartId), gWrapper);
        return GuestCartDTO.builder()
            .guestCartId(guestCartId)
            .cartItems(mapIdVsCartItem.values().stream().toList())
            .build();
    }

    public GuestCartDTO getGuestCart(String guestCartId){
        GuestCartWrapper gWrapper = (GuestCartWrapper) redisTemplate.opsForValue().get(getGuestUserKey(guestCartId));

        if(gWrapper == null){
            throw new RuntimeException("Invalid guest cart id");
        }

        Map<UUID, GuestCartItemWrapper> mapIdVsCartItem = gWrapper.getGuestCartItems();
        return GuestCartDTO.builder()
            .guestCartId(guestCartId)
            .cartItems(mapIdVsCartItem.values().stream().toList())
            .build();
    }

    private String getGuestUserKey(String guestId){
        return "guestCart:" + guestId;
    }
}
