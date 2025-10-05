package com.tanmay.e_commerce_application.inventory_service.DTO.Events;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentEvent {
    private String orderId;
    private String userId;
}
