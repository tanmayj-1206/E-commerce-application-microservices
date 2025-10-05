package com.tanmay.e_commerce_application.inventory_service.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.inventory_service.DTO.Request.InventoryRequestDTO;
import com.tanmay.e_commerce_application.inventory_service.DTO.Response.InventoryResponseDTO;
import com.tanmay.e_commerce_application.inventory_service.Entity.Inventory;
import com.tanmay.e_commerce_application.inventory_service.Repository.InventoryRepo;

@Service
public class InventoryService {
    final private String reservationKey = "product:reservation";

    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public InventoryResponseDTO updateInventory(InventoryRequestDTO iDto) {
        Inventory inventory = inventoryRepo.findByVariantId(UUID.fromString(iDto.getVariantId()))
            .map(inv -> {
                inv.setStock(inv.getStock() + iDto.getNewStock());
                return inv;
            })
            .orElse(Inventory.builder().stock(iDto.getNewStock()).variantId(UUID.fromString(iDto.getVariantId())).build());
        return InventoryResponseDTO.fromEntity(inventoryRepo.save(inventory));
    }

    public InventoryResponseDTO getInventory(String id){
        final Inventory inventory = inventoryRepo.findByVariantId(UUID.fromString(id))
            .orElse(new Inventory());
        
        Integer reserved = 0;
        if(redisTemplate.opsForHash().hasKey(reservationKey, inventory.getVariantId().toString())){
            reserved = Integer.valueOf(redisTemplate.opsForHash().get(reservationKey, inventory.getVariantId().toString()).toString());
        }
        return InventoryResponseDTO.fromEntity(inventory, reserved);
    }

    public Map<String, InventoryResponseDTO> getInventories(Set<UUID> idList){
        return inventoryRepo.findByVariantIdIn(idList).stream()
            .map(inv -> {
                Integer reserved = 0;
                if(redisTemplate.opsForHash().hasKey(reservationKey, inv.getVariantId().toString())){
                    reserved = Integer.valueOf(redisTemplate.opsForHash().get(reservationKey, inv.getVariantId().toString()).toString());
                }
                return InventoryResponseDTO.fromEntity(inv, reserved);
            })
            .collect(Collectors.toMap(i -> i.getVariantId(), i -> i));
    }

}
