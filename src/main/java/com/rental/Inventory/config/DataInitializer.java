package com.rental.Inventory.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rental.Inventory.entity.Roles;
import com.rental.Inventory.entity.Users;
import com.rental.Inventory.repository.RolesRepository;
import com.rental.Inventory.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeDefaultData(
            RolesRepository rolesRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            Roles ownerRole = ensureRole(
                    rolesRepository,
                    "owner",
                    "ini adalah owner");
            ensureRole(
                    rolesRepository,
                    "cashier",
                    "this is role a cashier");
            ensureRole(
                    rolesRepository,
                    "warehouse",
                    "this is role for warehouse");

            if (!userRepository.existsByUsername("admin")) {
                LocalDateTime now = LocalDateTime.now();
                Users owner = new Users();
                owner.setFullName("Owner Lentera Nadya Dja");
                owner.setUsername("admin");
                owner.setPassword(passwordEncoder.encode("admin"));
                owner.setRoles(ownerRole);
                owner.setStatus(true);
                owner.setCreateAt(now);
                owner.setUpdateAt(now);
                userRepository.save(owner);
            }
        };
    }

    private Roles ensureRole(
            RolesRepository rolesRepository,
            String roleName,
            String roleDescription) {
        Roles role = rolesRepository.findByRoleName(roleName).orElseGet(Roles::new);
        role.setRoleName(roleName);
        role.setRoleDescription(roleDescription);
        return rolesRepository.save(role);
    }
}
