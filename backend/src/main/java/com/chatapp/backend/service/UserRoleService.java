package com.chatapp.backend.service;

import com.chatapp.backend.model.Group;
import com.chatapp.backend.model.User;
import com.chatapp.backend.model.UserRole;

public interface UserRoleService {
    
    UserRole getUserRole(User user, Group group);
    UserRole saveUserRole(UserRole userRole);
    void updateUserRole(UserRole userRole);

}
