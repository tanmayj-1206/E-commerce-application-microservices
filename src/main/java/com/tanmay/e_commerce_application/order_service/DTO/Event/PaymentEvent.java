package com.tanmay.e_commerce_application.order_service.DTO.Event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentEvent {
    private String orderId;
    private String userId;
}
