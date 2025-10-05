package com.tanmay.e_commerce_application.inventory_service.Entity;

import java.util.UUID;

import com.tanmay.e_commerce_application.inventory_service.DTO.Events.VariantEvent;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID variantId;
    private Integer stock;

    public static Inventory toEntity(VariantEvent vEvent){
        return Inventory.builder()
            .variantId(vEvent.getVariantId())
            .stock(0)
            .build();
    }
}
