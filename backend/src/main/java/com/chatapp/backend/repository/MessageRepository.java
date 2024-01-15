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
    List<Message> findByUserFromAndUserToOrUserFromAndUserToOrderByCreatedAtDesc(User userFrom1, User userTo1, User userFrom2, User userTo2, Pageable pageable);
    List<Message> findByUserFromAndGroupToOrderByCreatedAtDesc(User user, Group group, Pageable pageable);

}
