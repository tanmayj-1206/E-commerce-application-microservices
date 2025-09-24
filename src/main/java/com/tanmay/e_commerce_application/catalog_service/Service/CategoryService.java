package com.tanmay.e_commerce_application.catalog_service.Service;

import java.util.List;
import java.util.UUID;
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
        categoryRepo.save(Category.toEntity(category));
    }

    public void updateCategory(String id, CategoryRequestDTO category) {
        categoryRepo.findById(UUID.fromString(id))
            .map(c -> categoryRepo.save(Category.toEntity(category, c.getId())))
            .orElseThrow(() -> new RuntimeException("Invalid id"));
    }
}
