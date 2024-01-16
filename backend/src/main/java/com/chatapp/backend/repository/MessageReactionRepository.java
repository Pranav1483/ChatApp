package com.chatapp.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.backend.model.Message;
import com.chatapp.backend.model.MessageReaction;
import com.chatapp.backend.model.User;

public interface MessageReactionRepository extends JpaRepository<MessageReaction, Long>{
    
    List<MessageReaction> findByMessage(Message message);
    Optional<MessageReaction> findByMessageAndUser(Message message, User user);
    void deleteById(Long id);

}
