package com.chatapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.backend.model.MessageReaction;

public interface MessageReactionRepository extends JpaRepository<MessageReaction, Long>{
    
}
