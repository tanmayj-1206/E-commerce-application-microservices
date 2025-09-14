package com.tanmay.e_commerce_application.auth_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanmay.e_commerce_application.auth_service.Model.UserModel;
import com.tanmay.e_commerce_application.auth_service.Service.AuthService;
import com.tanmay.e_commerce_application.auth_service.Wrapper.APIResponseWrapper;
import com.tanmay.e_commerce_application.auth_service.Wrapper.LoginRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel request) {
        authService.register(request);
        return ResponseEntity.ok(APIResponseWrapper.success("User registered successfully", null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(APIResponseWrapper.success("User logged in successfully", token));
    }
}
