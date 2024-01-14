package com.chatapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.backend.model.User;

public interface UsersRepository extends JpaRepository<User, Long>{
    
}
