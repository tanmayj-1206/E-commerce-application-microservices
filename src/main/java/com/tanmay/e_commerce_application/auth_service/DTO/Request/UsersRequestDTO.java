package com.tanmay.e_commerce_application.auth_service.DTO.Request;

import com.tanmay.e_commerce_application.auth_service.Enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersRequestDTO {
    private String username;
    private String password;
    private Roles role;
    private String email;
}
