package com.tanmay.e_commerce_application.catalog_service.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tanmay.e_commerce_application.catalog_service.Entity.ProductVariant;

public interface ProductVariantRepo extends JpaRepository<ProductVariant, UUID> {
    @EntityGraph(attributePaths = {"productId"})
    @Query("SELECT pv FROM ProductVariant pv WHERE pv.id = :id")
    Optional<ProductVariant> findWithProduct(@Param("id") UUID id);
}
