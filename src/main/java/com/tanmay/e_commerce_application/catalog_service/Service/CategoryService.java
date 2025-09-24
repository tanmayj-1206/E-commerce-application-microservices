package com.tanmay.e_commerce_application.catalog_service.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.catalog_service.DTO.Request.CategoryRequestDTO;
import com.tanmay.e_commerce_application.catalog_service.DTO.Response.CategoryResponseDTO;
import com.tanmay.e_commerce_application.catalog_service.Entity.Category;
import com.tanmay.e_commerce_application.catalog_service.Repository.CategoryRepo;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public List<CategoryResponseDTO> getCategories(){
        return categoryRepo.findAllWithProducts().stream()
            .map(CategoryResponseDTO::fromEntity)
            .toList();
    }

    public void addCategory(CategoryRequestDTO category) {
        Optional.ofNullable(category.getParentId())
            .map(id -> UUID.fromString(id))
            .flatMap(uiid -> categoryRepo.findById(uiid))
            .ifPresentOrElse(
                    c -> categoryRepo.save(Category.toEntity(category, c)), 
                    () -> categoryRepo.save(Category.toEntity(category, null))
                );
    }

    public void updateCategory(String id, CategoryRequestDTO category) {
        List<UUID> categoryUuids = Stream.of(id, category.getParentId())
            .filter(Objects::nonNull)
            .map(UUID::fromString)
            .toList();
        
        Map<String, Category> mapIdVsCategory = categoryRepo.findAllById(categoryUuids)
            .stream()
            .collect(Collectors.toMap(c -> String.valueOf(c.getId()), c -> c));

        Optional.ofNullable(mapIdVsCategory.get(id))
            .map(c -> categoryRepo.save(Category.toEntity(category, mapIdVsCategory.get(category.getParentId()), c.getId())))
            .orElseThrow(() -> new RuntimeException("Invalid category id"));
    }
}
