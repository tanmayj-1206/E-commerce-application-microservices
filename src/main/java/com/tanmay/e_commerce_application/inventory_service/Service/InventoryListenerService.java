package com.tanmay.e_commerce_application.inventory_service.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanmay.e_commerce_application.inventory_service.DTO.Events.OrderEvent;
import com.tanmay.e_commerce_application.inventory_service.DTO.Events.PaymentEvent;
import com.tanmay.e_commerce_application.inventory_service.DTO.Events.VariantEvent;
import com.tanmay.e_commerce_application.inventory_service.DTO.Response.OrderItemResponseDTO;
import com.tanmay.e_commerce_application.inventory_service.DTO.Wrapper.ApiResponseWrapper;
import com.tanmay.e_commerce_application.inventory_service.Entity.Inventory;
import com.tanmay.e_commerce_application.inventory_service.FeignClientInterface.OrderClient;
import com.tanmay.e_commerce_application.inventory_service.Repository.InventoryRepo;

import jakarta.transaction.Transactional;

@Service
public class InventoryListenerService {
    final private String reservationKey = "product:reservation";
    final private String expirationKey = "product:expire";

    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private OrderClient oClient;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @KafkaListener(topics = "VARIANT.CREATED")
    public void createInventory(String message) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        VariantEvent vEvent = mapper.readValue(message, VariantEvent.class);
        inventoryRepo.save(Inventory.toEntity(vEvent));
    }

    @KafkaListener(topics = "ORDER.CREATED")
    public void reserveInventory(String message) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        OrderEvent oEvent = mapper.readValue(message, OrderEvent.class);
        Long expiration = Instant.now().plus(30, ChronoUnit.MINUTES).toEpochMilli();
        redisTemplate.opsForZSet().add(expirationKey, oEvent.getOrderId().toString(), expiration);
        oEvent.getOrderItems().forEach(oi -> {
            redisTemplate.opsForHash().increment(reservationKey, oi.getVariantId().toString(), oi.getQuantity());
        });
    }

    @Transactional
    @KafkaListener(topics = "PAYMENT.SUCCESS")
    public void updateInventory(String message) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        PaymentEvent pEvent = mapper.readValue(message, PaymentEvent.class);
        ResponseEntity<ApiResponseWrapper<Map<UUID, List<OrderItemResponseDTO>>>> resp = oClient.getOrderItems(Set.of(UUID.fromString(pEvent.getOrderId())));

        if(resp.getStatusCode() != HttpStatus.OK || resp.getBody() == null){
            return;
        }
        redisTemplate.opsForZSet().remove(expirationKey, pEvent.getOrderId());
        Map<UUID, List<OrderItemResponseDTO>> mapIdVsOrderItems = resp.getBody().getPayLoad();

        Map<UUID, Integer> mapVariantIdVsQuantity = new HashMap<>();
        mapIdVsOrderItems.get(UUID.fromString(pEvent.getOrderId())).forEach(oi -> {
            redisTemplate.opsForHash().delete(reservationKey, oi.getVariantId().toString());
            Integer reservedStock = oi.getQuantity();
            if(mapVariantIdVsQuantity.containsKey(oi.getVariantId())){
                reservedStock += mapVariantIdVsQuantity.get(oi.getVariantId());
            }
            mapVariantIdVsQuantity.put(oi.getVariantId(), reservedStock);
        });
        List<Inventory> inventories = new ArrayList<>();
        inventoryRepo.findByVariantIdIn(mapVariantIdVsQuantity.keySet()).forEach(inv -> {
            Integer updatedStock = inv.getStock() - mapVariantIdVsQuantity.get(inv.getVariantId());
            if(updatedStock < 0){
                // Fire reservation failed event
                return;
            }
            inv.setStock(updatedStock);
            inventories.add(inv);
        });

        inventoryRepo.saveAll(inventories);
    }

}
