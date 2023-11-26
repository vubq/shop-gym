package com.web.shopgym.services;

import com.web.shopgym.entities.Role;
import com.web.shopgym.enums.ERole;

import java.util.Optional;

public interface RoleService {

    public Optional<Role> findByName(ERole name);
}
