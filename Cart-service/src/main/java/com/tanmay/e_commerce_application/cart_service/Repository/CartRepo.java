package com.tanmay.e_commerce_application.cart_service.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tanmay.e_commerce_application.cart_service.Entity.Cart;


@Repository
public interface CartRepo extends JpaRepository<Cart, UUID> {

    @EntityGraph(attributePaths = {"cartItems"})
    @Query("SELECT c FROM Cart c WHERE c.userId = :userId")  
    Optional<Cart> findCartWithCartItems(@Param("userId") UUID userId);

    Optional<Cart> findByUserId(UUID userId);
}
