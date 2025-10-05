package com.tanmay.e_commerce_application.order_service.FeignInterface;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tanmay.e_commerce_application.order_service.DTO.Response.InventoryResponseDTO;
import com.tanmay.e_commerce_application.order_service.DTO.Wrappers.ApiResponseWrapper;

@FeignClient(name = "inventory-service", path = "api/inventory")
public interface InventoryClient {

    @PostMapping("getstocks")
    ResponseEntity<ApiResponseWrapper<Map<String, InventoryResponseDTO>>> getInventoryByIds (@RequestBody Set<UUID> idList);
}
