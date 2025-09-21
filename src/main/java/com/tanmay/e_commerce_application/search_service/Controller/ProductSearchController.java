package com.tanmay.e_commerce_application.search_service.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanmay.e_commerce_application.search_service.Service.ProductSearchService;
import com.tanmay.e_commerce_application.search_service.Wrapper.ApiResponseWrapper;
import com.tanmay.e_commerce_application.search_service.Wrapper.RequestWrapper;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import jakarta.validation.Valid;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@RestController
@RequestMapping("api/search")
public class ProductSearchController {

    @Autowired
    private ProductSearchService productSearchService;

    @GetMapping("")
    public ResponseEntity<ApiResponseWrapper<?>> getProducts(@Valid @ModelAttribute RequestWrapper req) throws ElasticsearchException, IOException {
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Products fetched successfully", productSearchService.getProducts(req))
        );
    }
    
    @GetMapping("suggestion")
    public ResponseEntity<ApiResponseWrapper<?>> getSuggestions(@ModelAttribute RequestWrapper req) throws ElasticsearchException, IOException {
        return ResponseEntity.ok(
            ApiResponseWrapper.success("Suggestions fetched successfully", productSearchService.getSuggestions(req.getQ()))
        );
    }
    
}
