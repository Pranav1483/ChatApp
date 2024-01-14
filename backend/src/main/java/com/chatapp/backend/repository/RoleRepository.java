package com.chatapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.backend.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
