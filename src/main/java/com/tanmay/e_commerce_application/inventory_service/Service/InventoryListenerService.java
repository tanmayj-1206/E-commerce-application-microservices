package com.tanmay.e_commerce_application.inventory_service.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanmay.e_commerce_application.inventory_service.DTO.Events.OrderEvent;
import com.tanmay.e_commerce_application.inventory_service.DTO.Events.VariantEvent;
import com.tanmay.e_commerce_application.inventory_service.Entity.Inventory;
import com.tanmay.e_commerce_application.inventory_service.Repository.InventoryRepo;

@Service
public class InventoryListenerService {
    final private String reservationKey = "product:reservation";
    final private String expirationKey = "product:expire";

    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @KafkaListener(groupId = "ecommerce", topics = "VARIANT.CREATED")
    public void createInventory(String message) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        VariantEvent vEvent = mapper.readValue(message, VariantEvent.class);
        inventoryRepo.save(Inventory.toEntity(vEvent));
    }

    @KafkaListener(groupId = "ecommerce", topics = "ORDER.CREATED")
    public void reserveInventory(String message) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        OrderEvent oEvent = mapper.readValue(message, OrderEvent.class);
        Long expiration = Instant.now().plus(30, ChronoUnit.MINUTES).toEpochMilli();
        redisTemplate.opsForZSet().add(expirationKey, oEvent.getOrderId().toString(), expiration);
        oEvent.getOrderItems().forEach(oi -> {
            redisTemplate.opsForHash().increment(reservationKey, oi.getVariantId(), oi.getQuantity());
        });
    }

}
