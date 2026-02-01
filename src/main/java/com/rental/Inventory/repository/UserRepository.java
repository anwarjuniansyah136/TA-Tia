package com.rental.Inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.Inventory.entity.Users;

public interface UserRepository extends JpaRepository<Users, String>{

    Optional<Users> findByUsername(String username);
    
}
