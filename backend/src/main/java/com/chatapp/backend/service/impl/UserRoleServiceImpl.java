package com.chatapp.backend.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chatapp.backend.model.Group;
import com.chatapp.backend.model.User;
import com.chatapp.backend.model.UserRole;
import com.chatapp.backend.repository.UserRoleRepository;
import com.chatapp.backend.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService{
    
    private UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRole getUserRole(User user, Group group) {
        Optional<UserRole> userRole = userRoleRepository.findByUserAndGroup(user, group);
        if (userRole.isPresent()) return userRole.get();
        else throw new NoSuchElementException("No UserRole Found");
    }

    @Override
    public UserRole saveUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public void updateUserRole(UserRole userRole) {
        userRoleRepository.save(userRole);
    }
}
