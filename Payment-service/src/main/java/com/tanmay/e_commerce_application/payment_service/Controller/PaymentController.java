package com.tanmay.e_commerce_application.payment_service.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.tanmay.e_commerce_application.payment_service.DTO.Request.OrderItemRequestDTO;
import com.tanmay.e_commerce_application.payment_service.DTO.Wrappers.ApiResponseWrapper;
import com.tanmay.e_commerce_application.payment_service.Service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("api/payment")
public class PaymentController {
    @Autowired
    private PaymentService pService;

    @PostMapping("checkout")
    public ResponseEntity<ApiResponseWrapper<?>> checkout(@RequestBody List<OrderItemRequestDTO> oDtos, @RequestHeader("X-User-Id") String userId) throws StripeException {   
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Payment link created", pService.checkout(oDtos, userId))
        );
    }

    @PostMapping("webhook")
    public void handleStripeEvents (@RequestBody String payload, @RequestHeader("Stripe-Signature") String signHeader) throws StripeException {
        pService.handeEvents(payload, signHeader);
    }
    
    
}
