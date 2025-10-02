package com.tanmay.e_commerce_application.order_service.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanmay.e_commerce_application.order_service.Entity.OrderItem;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, UUID> {

}
