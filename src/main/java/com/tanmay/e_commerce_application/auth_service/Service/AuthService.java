package com.tanmay.e_commerce_application.auth_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.auth_service.Model.UserModel;
import com.tanmay.e_commerce_application.auth_service.Repo.UserRepo;
import com.tanmay.e_commerce_application.auth_service.Utility.JwtUtil;
import com.tanmay.e_commerce_application.auth_service.Wrapper.LoginRequest;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailAuthService userDetailAuthService;

    @Autowired
    private UserRepo userRepo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void register(UserModel user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public String login(LoginRequest request) {
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
        String token = JwtUtil.generateToken(user);
        return token;
    }
}
