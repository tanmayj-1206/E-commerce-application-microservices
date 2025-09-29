package com.tanmay.e_commerce_application.cart_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanmay.e_commerce_application.cart_service.DTO.Request.CartItemRequestDTO;
import com.tanmay.e_commerce_application.cart_service.DTO.Wrapper.ApiResponseWrapper;
import com.tanmay.e_commerce_application.cart_service.Service.CartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("api/cart")
public class CartController {
    @Autowired
    private CartService cService;

    @GetMapping("")
    public ResponseEntity<ApiResponseWrapper<?>> getCartItems(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Cart items fetched", cService.getCartItems(userId))
        );
    }
    @PostMapping("add")
    public ResponseEntity<ApiResponseWrapper<?>> addCartItem(@RequestBody CartItemRequestDTO cDto, @RequestHeader("X-User-Id") String userId) {        
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Item added", cService.addCartItem(cDto, userId))
        );
    }

    @PostMapping("guest")
    public ResponseEntity<ApiResponseWrapper<?>> createGuestCart() {        
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Guest cart created", cService.createGuestCart())
        );
    }
    
    @GetMapping("guest/{id}")
    public ResponseEntity<ApiResponseWrapper<?>> getGuestCart(@PathVariable String id) {
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Guest cart fetched successfully", cService.getGuestCart(id))
        );
    }
    
    @PostMapping("guest/{id}")
    public ResponseEntity<ApiResponseWrapper<?>> addItemToGuestCart (@RequestBody CartItemRequestDTO cDto, @PathVariable String id) {
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Item added", cService.addItemToGuestCart(cDto, id))
        );
    }
    
    
}
