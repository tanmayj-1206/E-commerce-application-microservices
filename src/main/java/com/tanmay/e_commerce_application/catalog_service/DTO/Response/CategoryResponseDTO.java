package com.tanmay.e_commerce_application.catalog_service.DTO.Response;

import java.util.List;

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
    private String parentId;
    private String parentName;
    private List<CategoryResponseDTO> childCategories;

    public static CategoryResponseDTO fromEntity(Category category){
        return CategoryResponseDTO.builder()
            .id(String.valueOf(category.getId()))
            .name(category.getName())
            .parentId(category.getParentId() != null ? String.valueOf(category.getParentId().getId()) : null)
            .parentName(category.getParentId() != null ? category.getParentId().getName() : null)
            .childCategories(
                category.getChildren().stream()
                    .map(c -> CategoryResponseDTO.builder().id(String.valueOf(c.getId())).name(c.getName()).build())
                    .toList()
            )
            .build();
    }
}
