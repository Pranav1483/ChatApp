package com.chatapp.backend.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatapp.backend.model.User;
import com.chatapp.backend.repository.UsersRepository;
import com.chatapp.backend.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService{

    private UsersRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsersRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
}
