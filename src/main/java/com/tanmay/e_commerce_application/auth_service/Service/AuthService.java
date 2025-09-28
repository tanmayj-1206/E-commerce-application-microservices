package com.tanmay.e_commerce_application.auth_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.auth_service.DTO.Request.LoginRequest;
import com.tanmay.e_commerce_application.auth_service.DTO.Request.UsersRequestDTO;
import com.tanmay.e_commerce_application.auth_service.DTO.Response.LoginResponse;
import com.tanmay.e_commerce_application.auth_service.DTO.Response.UsersResponseDTO;
import com.tanmay.e_commerce_application.auth_service.Entity.Users;
import com.tanmay.e_commerce_application.auth_service.Repo.UserRepo;
import com.tanmay.e_commerce_application.auth_service.Utility.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailAuthService userDetailAuthService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UsersResponseDTO register(UsersRequestDTO user) {
        final Users u = userRepo.save(Users.toEntity(user, encoder.encode(user.getPassword())));
        return UsersResponseDTO.fromEntity(u);
    }

    public LoginResponse login(LoginRequest request) {
        try{
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword()
                )
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid username or password");
        }
        UserDetails user = userDetailAuthService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(user);
        return LoginResponse.jwtToken(token);
    }
}
