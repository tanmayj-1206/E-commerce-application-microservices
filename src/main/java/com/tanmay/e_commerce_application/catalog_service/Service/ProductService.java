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

    public ProductResponseDTO addProduct(ProductRequestDTO pDto) {
        return categoryRepo.findById(UUID.fromString(pDto.getCategoryId()))
            .map(c -> ProductResponseDTO.fromEntity(productRepository.save(Product.toEntity(pDto, c))))
            .orElseThrow(
                    () -> new RuntimeException("Invalid category id")
                );
    }

    public ProductResponseDTO updateProduct(String id, ProductRequestDTO pRequestDTO) {
        return productRepository.findById(UUID.fromString(id))
            .map(
                p -> categoryRepo.findById(UUID.fromString(pRequestDTO.getCategoryId()))
                    .map(c -> ProductResponseDTO.fromEntity(productRepository.save(Product.toEntity(pRequestDTO, c, p.getId()))))
                    .orElseThrow(() -> new RuntimeException("Invalid category id"))
                )
            .orElseThrow(() -> new RuntimeException("Invalid product id"));
    }

}
