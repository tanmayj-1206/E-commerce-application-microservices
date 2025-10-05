package com.tanmay.e_commerce_application.payment_service.DTO.Event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEventDTO {
    private String orderId;
    private String userId;
}
