package com.chatapp.backend.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chatapp.backend.enm.MessageType;
import com.chatapp.backend.model.Group;
import com.chatapp.backend.model.Message;
import com.chatapp.backend.model.User;
import com.chatapp.backend.repository.MessageRepository;
import com.chatapp.backend.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService{
    
    private MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message saveMessage(Message message) {
        if ((message.getUser_to() == null && message.getGroup_to() == null) || (message.getUser_to() != null && message.getGroup_to() != null)) {
            throw new IllegalArgumentException("Both user_to and group_to cannot be simultaneously empty or filled");
        }
        if (message.getType() != MessageType.TXT && message.getContentURL() == null) {
            throw new IllegalArgumentException("Multimedia URL empty");
        }
        return messageRepository.save(message);
    }

    @Override
    public Message getMessage(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            return message.get();
        }
        else {
            throw new NoSuchElementException("No message Found");
        }
    }

    @Override
    public void updateMessage(Message message) {
        if ((message.getUser_to() == null && message.getGroup_to() == null) || (message.getUser_to() != null && message.getGroup_to() != null)) {
            throw new IllegalArgumentException("Both user_to and group_to cannot be simultaneously empty or filled");
        }
        if (message.getType() != MessageType.TXT && message.getContentURL() == null) {
            throw new IllegalArgumentException("Multimedia URL empty");
        }
        messageRepository.save(message);
    }

    @Override
    public List<Message> getUserConvo(User user_from, User user_to, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Message> messages = messageRepository.findByUser_fromAndUser_toOrUser_fromAndUser_toOrderByCreatedAtDesc(user_from, user_to, user_to, user_from, pageable);
        return messages;
    }

    @Override
    public List<Message> getGroupConvo(User user_from, Group group_to, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Message> messages = messageRepository.findByUser_fromAndGroup_toOrderByCreatedAtDesc(user_from, group_to, pageable);
        return messages;
    }

}
