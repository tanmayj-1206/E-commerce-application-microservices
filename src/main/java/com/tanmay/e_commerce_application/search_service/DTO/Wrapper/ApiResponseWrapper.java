package com.tanmay.e_commerce_application.search_service.DTO.Wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponseWrapper<T> {
    private String message;
    private T payLoad;
    private boolean isSuccess;
    private String errorMessage;

    public ApiResponseWrapper(String message, T payLoad, boolean isSuccess, String errorMessage) {
        this.message = message;
        this.payLoad = payLoad;
        this.isSuccess = isSuccess;
        this.errorMessage = errorMessage;
    }

    public static <T> ApiResponseWrapper<T> success(String message, T payLoad) {
        return new ApiResponseWrapper<>(message, payLoad, true, null);
    }

    public static <T> ApiResponseWrapper<T> failure(String message, String errorMessage) {
        return new ApiResponseWrapper<>(message, null, false, errorMessage);
    }
}