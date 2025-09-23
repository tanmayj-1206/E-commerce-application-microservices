package com.tanmay.e_commerce_application.catalog_service.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {
    private String id;
    private String name;
    private String description;
    private String categoryId;
    private String categoryName;
    private String brand;
}
