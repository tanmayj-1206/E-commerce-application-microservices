package com.tanmay.e_commerce_application.catalog_service.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanmay.e_commerce_application.catalog_service.Entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, UUID> {

}
