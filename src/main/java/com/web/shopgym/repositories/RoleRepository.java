package com.web.shopgym.repositories;

import com.web.shopgym.entities.Role;
import com.web.shopgym.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByName(ERole name);
}
