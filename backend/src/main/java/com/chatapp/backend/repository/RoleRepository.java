package com.chatapp.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.backend.model.Group;
import com.chatapp.backend.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
    List<Role> findByGroup(Group group);
    void deleteById(Long id);

}
