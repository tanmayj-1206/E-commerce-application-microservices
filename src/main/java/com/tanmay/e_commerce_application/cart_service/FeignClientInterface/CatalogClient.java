package com.tanmay.e_commerce_application.cart_service.FeignClientInterface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tanmay.e_commerce_application.cart_service.DTO.Response.VariantResponseDTO;
import com.tanmay.e_commerce_application.cart_service.DTO.Wrapper.ApiResponseWrapper;

@FeignClient(name = "catalog-service", path = "api")
public interface CatalogClient {
    @GetMapping("variant/get/{id}")
    ResponseEntity<ApiResponseWrapper<VariantResponseDTO>>  getVariant(@PathVariable String id);
}
