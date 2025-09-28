package com.tanmay.e_commerce_application.auth_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanmay.e_commerce_application.auth_service.DTO.Request.AddressRequestDTO;
import com.tanmay.e_commerce_application.auth_service.DTO.Wrapper.APIResponseWrapper;
import com.tanmay.e_commerce_application.auth_service.Service.AddressService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("api/address")
public class AddressController {
    @Autowired
    private AddressService aService;

    @PostMapping("add")
    public ResponseEntity<APIResponseWrapper<?>> postMethodName(@RequestBody AddressRequestDTO aDto, @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(
            APIResponseWrapper.success("Address added", aService.addAddress(aDto, userId))
        );
    }
    
}
