package com.chatapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.backend.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{
    
}
