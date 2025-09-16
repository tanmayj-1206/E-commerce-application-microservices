package com.tanmay.e_commerce_application.auth_service.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanmay.e_commerce_application.auth_service.Entity.UserModel;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
}
