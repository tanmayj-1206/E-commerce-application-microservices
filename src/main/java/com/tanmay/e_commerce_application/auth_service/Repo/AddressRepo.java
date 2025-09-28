package com.tanmay.e_commerce_application.auth_service.Repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tanmay.e_commerce_application.auth_service.Entity.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, UUID> {

}
