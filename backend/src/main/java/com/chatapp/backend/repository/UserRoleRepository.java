package com.chatapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.backend.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
    
}
