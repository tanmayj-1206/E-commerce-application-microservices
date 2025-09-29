package com.tanmay.e_commerce_application.cart_service.FeignClientInterface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tanmay.e_commerce_application.cart_service.DTO.Response.InventoryResponseDTO;
import com.tanmay.e_commerce_application.cart_service.DTO.Wrapper.ApiResponseWrapper;

@FeignClient(name = "inventory-service", path = "api/inventory")
public interface InventoryClient {
    @GetMapping("get/{id}")
    ResponseEntity<ApiResponseWrapper<InventoryResponseDTO>> getInventory(@PathVariable String id);
}
