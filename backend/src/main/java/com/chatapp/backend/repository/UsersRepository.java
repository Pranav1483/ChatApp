package com.chatapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.backend.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long>{
    
}
