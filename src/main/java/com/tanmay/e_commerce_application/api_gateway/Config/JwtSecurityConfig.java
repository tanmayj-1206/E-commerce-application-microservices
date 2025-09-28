package com.tanmay.e_commerce_application.api_gateway.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.tanmay.e_commerce_application.api_gateway.Service.UserDetailsAuthService;
import com.tanmay.e_commerce_application.api_gateway.Util.JwtUtil;

import jakarta.ws.rs.core.HttpHeaders;
import reactor.core.publisher.Mono;

@Component
public class JwtSecurityConfig implements WebFilter{

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsAuthService uService;
    
    @SuppressWarnings("null")
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String headers = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(headers == null || !headers.startsWith("Bearer")){
            return chain.filter(exchange);
        }
        String token = headers.substring(7);
        String userName = jwtUtil.extractUsername(token);
        System.out.println(userName);
        return uService.findByUsername(userName)
            .filter(u -> jwtUtil.isTokenValid(token, u))
            .flatMap(user -> {
                UsernamePasswordAuthenticationToken uToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(uToken));
            })
            .switchIfEmpty(Mono.defer(() -> {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }));
    }

}
