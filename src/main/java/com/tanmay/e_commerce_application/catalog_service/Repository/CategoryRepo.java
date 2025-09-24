package com.tanmay.e_commerce_application.catalog_service.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tanmay.e_commerce_application.catalog_service.Entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, UUID>{

    @EntityGraph(attributePaths = {"products"})
    @Query("SELECT c FROM Category c")
    List<Category> findAllWithProducts();
}
