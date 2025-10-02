package com.tanmay.e_commerce_application.api_gateway.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtSecurityConfig jConfig;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http){
        http
            .csrf(csrf -> csrf.disable())
            .authorizeExchange(req -> req
                .pathMatchers("/api/auth/**").permitAll()
                .pathMatchers("/api/payment/webhook/**").permitAll()
                .pathMatchers("/api/cart/guest/**").permitAll()
                .pathMatchers("/api/poducts/admin/**").hasRole("ADMIN")
                .anyExchange().authenticated()
                )
            .addFilterBefore(jConfig, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }
}
