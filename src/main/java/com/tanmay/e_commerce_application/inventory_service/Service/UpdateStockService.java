package com.tanmay.e_commerce_application.inventory_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.inventory_service.DTO.Events.VariantEvent;
import com.tanmay.e_commerce_application.inventory_service.Entity.Inventory;
import com.tanmay.e_commerce_application.inventory_service.Repository.InventoryRepo;

@Service
public class UpdateStockService {
    @Autowired
    private InventoryRepo inventoryRepo;

    @KafkaListener(groupId = "ecommerce", topics = "VARIANT.CREATED")
    public void createInventory(VariantEvent vEvent){
        inventoryRepo.save(Inventory.toEntity(vEvent));
    }
}
