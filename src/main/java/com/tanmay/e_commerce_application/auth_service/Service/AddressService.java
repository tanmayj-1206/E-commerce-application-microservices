package com.tanmay.e_commerce_application.auth_service.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.auth_service.DTO.Request.AddressRequestDTO;
import com.tanmay.e_commerce_application.auth_service.DTO.Response.AddressResponseDTO;
import com.tanmay.e_commerce_application.auth_service.Entity.Address;
import com.tanmay.e_commerce_application.auth_service.Repo.AddressRepo;

@Service
public class AddressService {
    @Autowired
    private AddressRepo addressRepo;

    public AddressResponseDTO addAddress(AddressRequestDTO aDto, String userId){
        System.out.println(userId);
        final Address add = addressRepo.save(Address.toEntity(aDto, UUID.fromString(userId)));
        return AddressResponseDTO.fromEntity(add); 
    }
}
