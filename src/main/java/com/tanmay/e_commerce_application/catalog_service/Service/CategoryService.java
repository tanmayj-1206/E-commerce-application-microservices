package com.tanmay.e_commerce_application.catalog_service.Service;

import java.util.List;
import java.util.Optional;
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
        return categoryRepo.findAll().stream()
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
}
