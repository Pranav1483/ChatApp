package com.chatapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.backend.model.LatestMessage;
import com.chatapp.backend.model.User;

import java.util.List;
import java.util.Optional;


public interface LatestMessageRepository extends JpaRepository<LatestMessage, Long>{
    
    List<LatestMessage> findByUserOrInboxUser(User user, User inboxUser);
    Optional<LatestMessage> findByUserAndInboxUserOrUserAndInboxUser(User user, User inboxUser, User user2, User inboxUser2);

}
