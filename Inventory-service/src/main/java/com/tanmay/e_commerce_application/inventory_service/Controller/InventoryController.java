package com.tanmay.e_commerce_application.inventory_service.Controller;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanmay.e_commerce_application.inventory_service.DTO.Request.InventoryRequestDTO;
import com.tanmay.e_commerce_application.inventory_service.DTO.Wrapper.ApiResponseWrapper;
import com.tanmay.e_commerce_application.inventory_service.Service.InventoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("update")
    public ResponseEntity<ApiResponseWrapper<?>> updateInventory(@RequestBody InventoryRequestDTO iDto) {
        
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Inventory Updated", inventoryService.updateInventory(iDto))
        );
    }

    @GetMapping("get/{id}")
    public ResponseEntity<ApiResponseWrapper<?>> getInventory(@PathVariable String id) {
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Inventory fetched", inventoryService.getInventory(id))
        );
    }
    
    @PostMapping("getstocks")
    public ResponseEntity<ApiResponseWrapper<?>> getInventoryByIds (@RequestBody Set<UUID> idList) {
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Inventory fetched", inventoryService.getInventories(idList))
        );
    }
    
}
