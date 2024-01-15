package com.chatapp.backend.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chatapp.backend.model.Group;
import com.chatapp.backend.model.Message;
import com.chatapp.backend.model.User;

import java.util.List;
import java.util.Optional;



public interface MessageRepository extends JpaRepository<Message, Long>{
    
    Optional<Message> findById(Long id);
    List<Message> findByUser_fromAndUser_toOrUser_fromAndUser_toOrderByCreatedAtDesc(User user1, User user2, User user2Again, User user1Again, Pageable pageable);
    List<Message> findByUser_fromAndGroup_toOrderByCreatedAtDesc(User user, Group group, Pageable pageable);

}
