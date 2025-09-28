package com.tanmay.e_commerce_application.auth_service.Repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanmay.e_commerce_application.auth_service.Entity.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, UUID> {
    Optional<Users> findByUsername(String username);
}
