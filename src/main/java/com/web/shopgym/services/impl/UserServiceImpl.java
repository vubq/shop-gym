package com.web.shopgym.services.impl;

import com.web.shopgym.entities.User;
import com.web.shopgym.repositories.UserRepository;
import com.web.shopgym.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByUserName(String userName) {
        return this.userRepository.findByUserName(userName);
    }

    @Override
    public Boolean existsByUserName(String userName) {
        return this.userRepository.existsByUserName(userName);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }
}
