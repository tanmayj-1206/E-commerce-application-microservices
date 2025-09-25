package com.tanmay.e_commerce_application.search_service.DTO.Event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEvent {
    private String id;
    private String name;
    private String description;
    private String category;
    private Double price;
    private String brand;
}
