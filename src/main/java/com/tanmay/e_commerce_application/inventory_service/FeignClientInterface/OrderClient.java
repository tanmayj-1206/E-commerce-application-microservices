package com.tanmay.e_commerce_application.inventory_service.FeignClientInterface;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tanmay.e_commerce_application.inventory_service.DTO.Response.OrderItemResponseDTO;
import com.tanmay.e_commerce_application.inventory_service.DTO.Wrapper.ApiResponseWrapper;

@FeignClient(name = "order-service", path = "api/order")
public interface OrderClient {

    @PostMapping("getorderitems")
    ResponseEntity<ApiResponseWrapper<Map<UUID, List<OrderItemResponseDTO>>>> getOrderItems(@RequestBody Set<UUID> ids);
}
