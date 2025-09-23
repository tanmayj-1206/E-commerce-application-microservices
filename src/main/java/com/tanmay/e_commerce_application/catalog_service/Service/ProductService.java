package com.tanmay.e_commerce_application.catalog_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.catalog_service.DTO.Response.ProductResponseDTO;
import com.tanmay.e_commerce_application.catalog_service.Repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<ProductResponseDTO> getProducts(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ProductResponseDTO> productsPage = productRepository.findAll(pageable)
                                                        .map(ProductResponseDTO::fromEntity);
        return productsPage;
    }

    public ProductResponseDTO getProduct(String id) {
        return productRepository.findById(Long.valueOf(id))
                                .map(ProductResponseDTO::fromEntity)
                                .orElse(new ProductResponseDTO());
    }

}
