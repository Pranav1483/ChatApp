package com.chatapp.backend.service;

import java.util.List;

import com.chatapp.backend.model.Group;
import com.chatapp.backend.model.Role;

public interface RoleService {
    
    List<Role> getGroupRoles(Group group);
    Role saveRole(Role role);
    void updateRole(Role role);
    void deleteRole(Long id);

}
