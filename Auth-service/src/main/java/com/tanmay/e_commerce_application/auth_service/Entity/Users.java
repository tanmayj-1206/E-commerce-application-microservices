package com.tanmay.e_commerce_application.auth_service.Entity;

import java.util.UUID;

import com.tanmay.e_commerce_application.auth_service.DTO.Request.UsersRequestDTO;
import com.tanmay.e_commerce_application.auth_service.Enums.Roles;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Roles role;

    public static Users toEntity(UsersRequestDTO uDto, String password){
        return Users.builder()
            .username(uDto.getUsername())
            .email(uDto.getEmail())
            .password(password)
            .role(uDto.getRole())
            .build();
    }
}
