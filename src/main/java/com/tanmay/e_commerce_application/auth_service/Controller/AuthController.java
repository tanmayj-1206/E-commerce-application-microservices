package com.tanmay.e_commerce_application.auth_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanmay.e_commerce_application.auth_service.DTO.Request.LoginRequest;
import com.tanmay.e_commerce_application.auth_service.DTO.Request.UsersRequestDTO;
import com.tanmay.e_commerce_application.auth_service.DTO.Wrapper.APIResponseWrapper;
import com.tanmay.e_commerce_application.auth_service.Service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsersRequestDTO request) {
        return ResponseEntity.ok(APIResponseWrapper.success("User registered successfully", authService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(APIResponseWrapper.success("User logged in successfully", authService.login(request)));
    }
}
