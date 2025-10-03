package com.tanmay.e_commerce_application.order_service.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanmay.e_commerce_application.order_service.Entity.Order;
import java.util.Optional;


@Repository
public interface OrderRepo extends JpaRepository<Order, UUID> {
    Optional<Order> findByIdAndUserId(UUID id, UUID userId);
}
