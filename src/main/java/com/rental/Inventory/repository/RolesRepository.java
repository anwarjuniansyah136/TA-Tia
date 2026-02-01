package com.rental.Inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.Inventory.entity.Roles;

public interface RolesRepository extends JpaRepository<Roles, String>{
    Optional<Roles> findByRoleName(String roleName);
}
