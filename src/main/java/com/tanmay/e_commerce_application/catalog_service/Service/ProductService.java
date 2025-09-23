package com.tanmay.e_commerce_application.catalog_service.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.catalog_service.DTO.Request.ProductRequestDTO;
import com.tanmay.e_commerce_application.catalog_service.DTO.Response.ProductResponseDTO;
import com.tanmay.e_commerce_application.catalog_service.Entity.Product;
import com.tanmay.e_commerce_application.catalog_service.Repository.CategoryRepo;
import com.tanmay.e_commerce_application.catalog_service.Repository.ProductRepo;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private CategoryRepo categoryRepo;

    public Page<ProductResponseDTO> getProducts(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ProductResponseDTO> productsPage = productRepository.findAll(pageable)
                                                        .map(ProductResponseDTO::fromEntity);
        return productsPage;
    }

    public ProductResponseDTO getProduct(String id) {
        return productRepository.findById(UUID.fromString(id))
                                .map(ProductResponseDTO::fromEntity)
                                .orElse(new ProductResponseDTO());
    }

    public void addProduct(ProductRequestDTO pDto) {
        categoryRepo.findById(UUID.fromString(pDto.getCategoryId()))
            .map(c -> {
                Product product = Product.toEntity(pDto, c);
                productRepository.save(product);
                return c;
            }).orElseThrow(
                    () -> new RuntimeException("Invalid category id")
                );
    }

}
