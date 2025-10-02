package com.tanmay.e_commerce_application.payment_service.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanmay.e_commerce_application.payment_service.Entity.Payment;
import java.util.Optional;


@Repository
public interface PaymentRepo extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByOrderId(UUID orderId);
}
