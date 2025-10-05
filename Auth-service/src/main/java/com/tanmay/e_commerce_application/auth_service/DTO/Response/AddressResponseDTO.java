package com.tanmay.e_commerce_application.auth_service.DTO.Response;

import com.tanmay.e_commerce_application.auth_service.Entity.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponseDTO {
    private String id;
    private String userId;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private Integer postalCode;

    public static AddressResponseDTO fromEntity(Address address){
        return AddressResponseDTO.builder()
            .id(String.valueOf(address.getId()))
            .addressLine1(address.getAddressLine1())
            .addressLine2(address.getAddressLine2())
            .city(address.getCity())
            .country(address.getCountry())
            .state(address.getState())
            .postalCode(address.getPostalCode())
            .userId(String.valueOf(address.getUserId()))
            .build();
    }
}
