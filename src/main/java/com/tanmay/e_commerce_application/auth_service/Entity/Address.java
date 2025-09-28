package com.tanmay.e_commerce_application.auth_service.Entity;

import java.util.UUID;

import com.tanmay.e_commerce_application.auth_service.DTO.Request.AddressRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private Integer postalCode;

    public static Address toEntity(AddressRequestDTO aDto, UUID userId){
        return Address.builder()
            .addressLine1(aDto.getAddressLine1())
            .addressLine2(aDto.getAddressLine2())
            .city(aDto.getCity())
            .state(aDto.getState())
            .country(aDto.getCountry())
            .postalCode(aDto.getPostalCode())
            .userId(userId)
            .build();
    }
}
