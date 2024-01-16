package com.chatapp.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.backend.model.Group;
import com.chatapp.backend.model.User;
import com.chatapp.backend.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
    
    Optional<UserRole> findByUserAndGroup(User user, Group group);

}
