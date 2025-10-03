package com.tanmay.e_commerce_application.order_service.DTO.Event;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpiredOrderEvent {
    private Set<UUID> expiredOrders;
    private Map<UUID, Integer> variantIdVsQuantity;
}
