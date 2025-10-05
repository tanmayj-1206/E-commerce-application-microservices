package com.tanmay.e_commerce_application.order_service.Entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tanmay.e_commerce_application.order_service.DTO.Request.OrderItemReqDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order orderId;

    private UUID productVariantId;
    private Integer quantity;
    private Double price;

    public static OrderItem toEntity(OrderItemReqDTO oDto, Double price){
        return OrderItem.builder()
            .productVariantId(oDto.getVariantId())
            .quantity(oDto.getQuantity())
            .price(price)
            .build();
    }
}
