package com.tanmay.e_commerce_application.auth_service.DTO.Response;

import com.tanmay.e_commerce_application.auth_service.Entity.Users;
import com.tanmay.e_commerce_application.auth_service.Enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersResponseDTO {
    private String id;
    private String username;
    private Roles role;
    private String email;

    public static UsersResponseDTO fromEntity(Users user){
        return UsersResponseDTO.builder()
            .id(String.valueOf(user.getId()))
            .username(user.getUsername())
            .email(user.getEmail())
            .role(user.getRole())
            .build();
    }
}
