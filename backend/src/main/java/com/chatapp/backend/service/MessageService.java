package com.chatapp.backend.service;

import java.util.List;

import com.chatapp.backend.dto.MessageDTO;
import com.chatapp.backend.model.Group;
import com.chatapp.backend.model.Message;
import com.chatapp.backend.model.User;

public interface MessageService {
    
    Message saveMessage(Message message);
    Message getMessage(Long id);
    void updateMessage(Message message);
    List<Message> getUserConvo(User user_from, User user_to, int page, int size);
    List<Message> getGroupConvo(User user_from, Group group_to, int page, int size);
    Message DTOtoMessage(MessageDTO messageDTO, Long ToId, Boolean toGroup);

}
