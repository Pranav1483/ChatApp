package com.chatapp.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.backend.model.Connection;
import com.chatapp.backend.model.User;

public interface ConnectionRepository extends JpaRepository<Connection, Long>{
    
    Optional<Connection> findByUserFromAndUserTo(User userFrom, User userTo);

}
