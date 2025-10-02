package com.tanmay.e_commerce_application.order_service.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.order_service.DTO.Request.OrderItemReqDTO;
import com.tanmay.e_commerce_application.order_service.DTO.Response.OrderResponseDTO;
import com.tanmay.e_commerce_application.order_service.DTO.Response.VariantResponseDTO;
import com.tanmay.e_commerce_application.order_service.DTO.Wrappers.ApiResponseWrapper;
import com.tanmay.e_commerce_application.order_service.Entity.Order;
import com.tanmay.e_commerce_application.order_service.Entity.OrderItem;
import com.tanmay.e_commerce_application.order_service.Enums.Status;
import com.tanmay.e_commerce_application.order_service.FeignInterface.CatalogClient;
import com.tanmay.e_commerce_application.order_service.Repository.OrderRepo;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CatalogClient catalogClient;

    public OrderResponseDTO createOrder(List<OrderItemReqDTO> orderItems, String userId){
        Set<UUID> variantIds = orderItems.stream().map(OrderItemReqDTO::getVariantId).collect(Collectors.toSet());
        ResponseEntity<ApiResponseWrapper<Map<String, VariantResponseDTO>>> resp = catalogClient.getVariants(variantIds);

        if(resp.getStatusCode() != HttpStatus.OK || resp.getBody() == null){
            throw new RuntimeException("Service failure");
        }
        Map<String, VariantResponseDTO> mapIdVsVariant = resp.getBody().getPayLoad();

        Double totalAmount = orderItems.stream()
            .filter(o -> mapIdVsVariant.containsKey(String.valueOf(o.getVariantId())))
            .mapToDouble(oi -> oi.getQuantity() * mapIdVsVariant.get(String.valueOf(oi.getVariantId())).getPriceOverride())
            .sum();

        List<OrderItem> oItems = orderItems.stream()
            .filter(o -> mapIdVsVariant.containsKey(String.valueOf(o.getVariantId())))
            .map(oi -> OrderItem.toEntity(oi, mapIdVsVariant.get(String.valueOf(oi.getVariantId())).getPriceOverride()))
            .toList();

        Order order = Order.builder()
            .amount(totalAmount)
            .userId(UUID.fromString(userId))
            .orderItems(oItems)
            .orderStatus(Status.PENDING)
            .build();

        orderRepo.save(order);

        return OrderResponseDTO.fromEntity(order);
    }
}
