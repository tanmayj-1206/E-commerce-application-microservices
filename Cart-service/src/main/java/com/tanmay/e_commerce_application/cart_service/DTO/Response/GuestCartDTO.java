package com.tanmay.e_commerce_application.cart_service.DTO.Response;

import java.util.List;

import com.tanmay.e_commerce_application.cart_service.DTO.Wrapper.GuestCartItemWrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestCartDTO {
    private String guestCartId;
    private List<GuestCartItemWrapper> cartItems;
}
