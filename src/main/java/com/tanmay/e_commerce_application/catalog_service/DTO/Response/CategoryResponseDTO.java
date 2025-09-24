package com.tanmay.e_commerce_application.catalog_service.DTO.Response;

import com.tanmay.e_commerce_application.catalog_service.Entity.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDTO {
    private String id;
    private String name;
    private String description;

    public static CategoryResponseDTO fromEntity(Category category){
        return CategoryResponseDTO.builder()
            .id(String.valueOf(category.getId()))
            .name(category.getName())
            .description(category.getDescription())
            .build();
    }
}
