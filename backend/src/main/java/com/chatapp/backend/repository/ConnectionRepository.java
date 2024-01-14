package com.chatapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.backend.model.Connection;

public interface ConnectionRepository extends JpaRepository<Connection, Long>{
    
}
