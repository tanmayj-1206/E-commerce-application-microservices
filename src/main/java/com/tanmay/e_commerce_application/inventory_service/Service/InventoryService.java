package com.tanmay.e_commerce_application.inventory_service.Service;

import java.util.UUID;

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
            .orElseThrow(() -> new RuntimeException("Invalid variant Id"));
        return InventoryResponseDTO.fromEntity(inventoryRepo.save(inventory));
    }

    public InventoryResponseDTO getInventory(String id){
        final Inventory inventory = inventoryRepo.findByVariantId(UUID.fromString(id))
            .orElse(new Inventory());
        
            return InventoryResponseDTO.fromEntity(inventory);
    }

}
