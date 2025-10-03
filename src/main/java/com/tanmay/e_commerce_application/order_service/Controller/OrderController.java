package com.tanmay.e_commerce_application.order_service.Controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanmay.e_commerce_application.order_service.DTO.Request.OrderItemReqDTO;
import com.tanmay.e_commerce_application.order_service.DTO.Wrappers.ApiResponseWrapper;
import com.tanmay.e_commerce_application.order_service.Service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;



@RestController
@RequestMapping("api/order")
public class OrderController {

    @Autowired
    private OrderService oService;

    @PostMapping("create")
    public ResponseEntity<ApiResponseWrapper<?>> createOrder(@RequestBody List<OrderItemReqDTO> oDtos, @RequestHeader("X-User-Id") String userId) { 
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Order created", oService.createOrder(oDtos, userId))
        );
    }

    @PostMapping("getorderitems")
    public ResponseEntity<ApiResponseWrapper<?>> getOrderItems(@RequestBody Set<UUID> ids) {
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Order items fetched", oService.getOrderItems(ids))
        );
    }
    
    
}
