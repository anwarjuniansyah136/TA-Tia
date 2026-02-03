package com.rental.Inventory.service.impl;

import com.rental.Inventory.entity.Roles;
import com.rental.Inventory.repository.RolesRepository;
import com.rental.Inventory.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RolesRepository rolesRepository;

    public RoleServiceImpl(
            RolesRepository rolesRepository
    ){
        this.rolesRepository = rolesRepository;
    }

    @Override
    public List<Roles> findAllRole() {
        return rolesRepository.findAll();
    }
}
