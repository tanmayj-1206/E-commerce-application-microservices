package com.tanmay.e_commerce_application.inventory_service.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanmay.e_commerce_application.inventory_service.Entity.Inventory;

import java.util.List;
import java.util.Optional;
import java.util.Set;



@Repository
public interface InventoryRepo extends JpaRepository<Inventory, UUID> {

    Optional<Inventory> findByVariantId(UUID variantId);

    List<Inventory> findByVariantIdIn(Set<UUID> variants);
}
