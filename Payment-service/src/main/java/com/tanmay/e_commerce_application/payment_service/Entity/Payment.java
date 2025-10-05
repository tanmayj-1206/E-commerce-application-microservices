package com.tanmay.e_commerce_application.payment_service.Entity;

import java.util.UUID;

import com.tanmay.e_commerce_application.payment_service.Enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID orderId;
    private String paymentIntentId;
    private String invoiceId;
    private String invoicePdf;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
