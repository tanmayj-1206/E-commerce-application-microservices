package com.tanmay.e_commerce_application.catalog_service.DTO.Wrappers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseWrapper<T> {
    private String message;
    private T payLoad;
    private boolean isSuccess;
    private String errorMessage;

    public static <T> ApiResponseWrapper<T> success(String message, T payLoad) {
        return ApiResponseWrapper.<T>builder()
            .message(message)
            .payLoad(payLoad)
            .build();
    }

    public static <T> ApiResponseWrapper<T> failure(String message, String errorMessage) {
        return ApiResponseWrapper.<T>builder()
            .message(message)
            .errorMessage(errorMessage)
            .build();
    }
}
