package com.tanmay.e_commerce_application.order_service.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.order_service.DTO.Event.OrderEvent;
import com.tanmay.e_commerce_application.order_service.DTO.Request.OrderItemReqDTO;
import com.tanmay.e_commerce_application.order_service.DTO.Response.InventoryResponseDTO;
import com.tanmay.e_commerce_application.order_service.DTO.Response.OrderItemResponseDTO;
import com.tanmay.e_commerce_application.order_service.DTO.Response.OrderResponseDTO;
import com.tanmay.e_commerce_application.order_service.DTO.Response.VariantResponseDTO;
import com.tanmay.e_commerce_application.order_service.DTO.Wrappers.ApiResponseWrapper;
import com.tanmay.e_commerce_application.order_service.Entity.Order;
import com.tanmay.e_commerce_application.order_service.Entity.OrderItem;
import com.tanmay.e_commerce_application.order_service.Enums.Status;
import com.tanmay.e_commerce_application.order_service.FeignInterface.CatalogClient;
import com.tanmay.e_commerce_application.order_service.FeignInterface.InventoryClient;
import com.tanmay.e_commerce_application.order_service.Repository.OrderRepo;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CatalogClient catalogClient;

    @Autowired
    private InventoryClient iClient;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderResponseDTO createOrder(List<OrderItemReqDTO> orderItems, String userId){
        Set<UUID> variantIds = orderItems.stream().map(OrderItemReqDTO::getVariantId).collect(Collectors.toSet());
        ResponseEntity<ApiResponseWrapper<Map<String, VariantResponseDTO>>> resp = catalogClient.getVariants(variantIds);
        ResponseEntity<ApiResponseWrapper<Map<String, InventoryResponseDTO>>> response = iClient.getInventoryByIds(variantIds);

        if(resp.getStatusCode() != HttpStatus.OK || resp.getBody() == null || response.getStatusCode() != HttpStatus.OK || response.getBody() == null){
            throw new RuntimeException("Service failure");
        }
        Map<String, VariantResponseDTO> mapIdVsVariant = resp.getBody().getPayLoad();
        Map<String, InventoryResponseDTO> mapIdVsInventory = response.getBody().getPayLoad();

        Boolean inventoryUnavailable = orderItems.stream()
            .filter(o -> mapIdVsInventory.containsKey(o.getVariantId().toString()))
            .anyMatch(oi -> oi.getQuantity() > mapIdVsInventory.get(oi.getVariantId().toString()).getStock());

        if(inventoryUnavailable){
            throw new RuntimeException("Product unavailable");
        }

        Double totalAmount = orderItems.stream()
            .filter(o -> mapIdVsVariant.containsKey(String.valueOf(o.getVariantId())) && mapIdVsInventory.containsKey(o.getVariantId().toString()))
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
        
        oItems.forEach(oi -> oi.setOrderId(order));

        orderRepo.save(order);

        kafkaTemplate.send("ORDER.CREATED", OrderEvent.fromEntity(order));

        return OrderResponseDTO.fromEntity(order, mapIdVsVariant);
    }

    public Map<UUID, List<OrderItemResponseDTO>> getOrderItems(Set<UUID> orderIds){
        Map<UUID, List<OrderItemResponseDTO>> mapIdVsOrderItems = new HashMap<>();
        orderRepo.findAllOrderWithItems(orderIds).forEach(o -> {
            List<OrderItemResponseDTO> oDtos = o.getOrderItems().stream()
                .map(OrderItemResponseDTO::fromEntity)
                .toList();
            mapIdVsOrderItems.put(o.getId(), oDtos); 
        });

        return mapIdVsOrderItems;
    }
}
