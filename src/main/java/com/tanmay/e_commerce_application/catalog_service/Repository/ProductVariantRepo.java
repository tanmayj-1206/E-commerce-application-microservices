package com.tanmay.e_commerce_application.catalog_service.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tanmay.e_commerce_application.catalog_service.Entity.ProductVariant;

public interface ProductVariantRepo extends JpaRepository<ProductVariant, UUID> {

}
