package com.tanmay.e_commerce_application.payment_service.DTO.Event;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExpiredOrdersEvent {
    private Set<UUID> expiredOrders;
    private Map<UUID, Integer> variantIdVsQuantity;
}
