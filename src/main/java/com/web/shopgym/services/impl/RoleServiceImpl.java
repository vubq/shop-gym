package com.web.shopgym.services.impl;

import com.web.shopgym.entities.Role;
import com.web.shopgym.enums.ERole;
import com.web.shopgym.repositories.RoleRepository;
import com.web.shopgym.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(ERole name) {
        return this.roleRepository.findByName(name);
    }
}
