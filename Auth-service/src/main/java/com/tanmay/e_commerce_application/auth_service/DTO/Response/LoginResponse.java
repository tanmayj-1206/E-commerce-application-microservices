package com.tanmay.e_commerce_application.auth_service.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String jwtToken;

    public static LoginResponse jwtToken(String token){
        return LoginResponse.builder()
            .jwtToken(token)
            .build();
    }
}
