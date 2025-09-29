package com.tanmay.e_commerce_application.cart_service.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanmay.e_commerce_application.cart_service.Entity.CartItems;
import java.util.Optional;



@Repository
public interface CartItemRepo extends JpaRepository<CartItems, UUID> {
    Optional<CartItems> findByVariantIdAndProductIdAndCartId_Id(UUID variantId, UUID productId, UUID cartId_Id);
}
