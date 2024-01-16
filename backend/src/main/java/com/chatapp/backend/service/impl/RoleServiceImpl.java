package com.chatapp.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chatapp.backend.model.Group;
import com.chatapp.backend.model.Role;
import com.chatapp.backend.repository.RoleRepository;
import com.chatapp.backend.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
    
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getGroupRoles(Group group) {
        List<Role> roles = roleRepository.findByGroup(group);
        return roles;
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void updateRole(Role role) {
        roleRepository.save(role);
    }

    @Override 
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

}
