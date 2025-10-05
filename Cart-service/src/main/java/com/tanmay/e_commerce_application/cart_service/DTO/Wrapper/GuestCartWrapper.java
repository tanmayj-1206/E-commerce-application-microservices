package com.tanmay.e_commerce_application.cart_service.DTO.Wrapper;

import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuestCartWrapper {
    private UUID guestCartId;
    private Map<UUID, GuestCartItemWrapper> guestCartItems;
}
