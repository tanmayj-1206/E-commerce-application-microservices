package com.tanmay.e_commerce_application.catalog_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanmay.e_commerce_application.catalog_service.DTO.Request.CategoryRequestDTO;
import com.tanmay.e_commerce_application.catalog_service.DTO.Wrappers.ApiResponseWrapper;
import com.tanmay.e_commerce_application.catalog_service.Service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("getcategories")
    public ResponseEntity<ApiResponseWrapper<?>> getCategories() {
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Categories fetched successfully", categoryService.getCategories())
        );
    }
    
    @PostMapping("addcategory")
    public ResponseEntity<ApiResponseWrapper<?>> addCategory(@RequestBody CategoryRequestDTO category) {
        categoryService.addCategory(category);
        
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Category added successfully", null)
        );
    }
    
}
