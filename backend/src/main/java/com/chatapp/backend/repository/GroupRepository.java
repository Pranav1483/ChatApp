package com.chatapp.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.backend.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long>{
    
    Optional<Group> findById(Long id);
    

}
