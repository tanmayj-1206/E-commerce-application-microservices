package com.tanmay.e_commerce_application.payment_service.FeignClients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.tanmay.e_commerce_application.payment_service.DTO.Request.OrderItemRequestDTO;
import com.tanmay.e_commerce_application.payment_service.DTO.Response.OrderResponseDTO;
import com.tanmay.e_commerce_application.payment_service.DTO.Wrappers.ApiResponseWrapper;

@FeignClient(name = "order-service", path = "api/order")
public interface OrderClient {
    @GetMapping("create")
    ResponseEntity<ApiResponseWrapper<OrderResponseDTO>> createOrder(@RequestBody List<OrderItemRequestDTO> oDtos, @RequestHeader("X-User-Id") String userId);
}
