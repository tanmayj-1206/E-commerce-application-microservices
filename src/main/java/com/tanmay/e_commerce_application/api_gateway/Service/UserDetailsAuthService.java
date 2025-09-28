package com.tanmay.e_commerce_application.api_gateway.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.tanmay.e_commerce_application.api_gateway.Repository.UserRepo;

import reactor.core.publisher.Mono;

@Component
public class UserDetailsAuthService implements ReactiveUserDetailsService{
    @Autowired
    private UserRepo uRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.just(uRepo.findByUsername(username)
            .map(u -> User.builder()
                .username(u.getUsername())
                .password(passwordEncoder.encode(u.getPassword()))
                .roles(u.getRole())
                .build()
                )
            .orElseThrow(() -> new RuntimeException("Invalid username"))
            );
    }

}
