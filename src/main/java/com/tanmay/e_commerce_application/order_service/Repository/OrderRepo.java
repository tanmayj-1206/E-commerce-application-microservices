package com.tanmay.e_commerce_application.order_service.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tanmay.e_commerce_application.order_service.Entity.Order;

import java.util.List;
import java.util.Optional;
import java.util.Set;



@Repository
public interface OrderRepo extends JpaRepository<Order, UUID> {
    Optional<Order> findByIdAndUserId(UUID id, UUID userId);

    @EntityGraph(attributePaths = {"orderItems"})
    @Query("SELECT o FROM Order o WHERE o.id IN :ids")
    List<Order> findAllOrderWithItems(Set<UUID> ids);
}
