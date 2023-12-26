package com.web.shopgym.services;

import com.web.shopgym.entities.User;

import java.util.Optional;

public interface UserService {

    public Optional<User> findByUserName(String userName);
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);

    public Optional<User> findById(String id);
}
