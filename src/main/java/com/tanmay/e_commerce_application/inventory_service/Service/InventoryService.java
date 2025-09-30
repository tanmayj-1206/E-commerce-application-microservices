package com.tanmay.e_commerce_application.inventory_service.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.inventory_service.DTO.Request.InventoryRequestDTO;
import com.tanmay.e_commerce_application.inventory_service.DTO.Response.InventoryResponseDTO;
import com.tanmay.e_commerce_application.inventory_service.Entity.Inventory;
import com.tanmay.e_commerce_application.inventory_service.Repository.InventoryRepo;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

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
        
            return InventoryResponseDTO.fromEntity(inventory);
    }

    public Map<String, InventoryResponseDTO> getInventories(Set<UUID> idList){
        return inventoryRepo.findByVariantIdIn(idList).stream()
            .map(inv -> {
                System.out.println(inv);
                return InventoryResponseDTO.fromEntity(inv);
            })
            .collect(Collectors.toMap(i -> i.getVariantId(), i -> i));
    }

}
