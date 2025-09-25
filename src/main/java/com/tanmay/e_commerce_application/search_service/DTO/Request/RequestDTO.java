package com.tanmay.e_commerce_application.search_service.DTO.Request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestDTO {

    @NotNull(message = "Product name is required")
    private String q;
    private String category;
    private String sort;

    @Min(value = 0, message = "Page value cannot be less than 0")
    private int page = 0;

    @Min(value = 0, message = "Page size cannot be less than 0")
    @Max(value = 100, message = "Size should be less than 100")
    private int size = 20;
}
