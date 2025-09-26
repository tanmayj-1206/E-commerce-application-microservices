package com.tanmay.e_commerce_application.inventory_service.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanmay.e_commerce_application.inventory_service.Entity.Inventory;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, UUID> {

}
