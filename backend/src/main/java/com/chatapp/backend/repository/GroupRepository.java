package com.chatapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.backend.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long>{
    
}
