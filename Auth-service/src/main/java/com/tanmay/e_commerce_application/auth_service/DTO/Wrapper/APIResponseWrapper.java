package com.tanmay.e_commerce_application.auth_service.DTO.Wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class APIResponseWrapper<T> {
    private boolean isSuccess;
    private String message;
    private T data;
    private String errorMessage;

    public APIResponseWrapper(boolean isSuccess, String message, T data, String errorMessage) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public static <T> APIResponseWrapper<T> success(String message, T data) {
        return new APIResponseWrapper<>(true, message, data, null);
    }

    public static <T> APIResponseWrapper<T> failure(String errorMessage) {
        return new APIResponseWrapper<>(false, null, null, errorMessage);
    }
}
