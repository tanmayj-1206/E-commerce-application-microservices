package com.tanmay.e_commerce_application.auth_service.DTO.Request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressRequestDTO {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private Integer postalCode;
}
