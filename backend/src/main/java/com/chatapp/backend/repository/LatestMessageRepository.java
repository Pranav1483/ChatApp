package com.chatapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.backend.model.LatestMessage;
import com.chatapp.backend.model.User;

import java.util.List;


public interface LatestMessageRepository extends JpaRepository<LatestMessage, Long>{
    
    List<LatestMessage> findByUser(User user);

}
