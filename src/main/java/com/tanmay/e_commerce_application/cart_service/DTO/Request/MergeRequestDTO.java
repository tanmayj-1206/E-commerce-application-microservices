package com.tanmay.e_commerce_application.cart_service.DTO.Request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MergeRequestDTO {
    private String guestCartId;
    private String idempotencyKey;
}
