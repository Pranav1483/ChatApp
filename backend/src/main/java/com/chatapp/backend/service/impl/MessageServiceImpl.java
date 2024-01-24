package com.chatapp.backend.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chatapp.backend.dto.MessageDTO;
import com.chatapp.backend.enm.MessageType;
import com.chatapp.backend.model.Group;
import com.chatapp.backend.model.Message;
import com.chatapp.backend.model.User;
import com.chatapp.backend.repository.GroupRepository;
import com.chatapp.backend.repository.MessageRepository;
import com.chatapp.backend.repository.UserRepository;
import com.chatapp.backend.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService{
    
    private MessageRepository messageRepository;
    private UserRepository userRepository;
    private GroupRepository groupRepository;

    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository, GroupRepository groupRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public Message saveMessage(Message message) {
        if ((message.getUserTo() == null && message.getGroupTo() == null) || (message.getUserTo() != null && message.getGroupTo() != null)) {
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
        if ((message.getUserTo() == null && message.getGroupTo() == null) || (message.getUserTo() != null && message.getGroupTo() != null)) {
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
        List<Message> messages = messageRepository.findByUserFromAndUserToOrUserFromAndUserToOrderByCreatedAtDesc(user_from, user_to, user_to, user_from, pageable);
        return messages;
    }

    @Override
    public List<Message> getGroupConvo(User user_from, Group group_to, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Message> messages = messageRepository.findByUserFromAndGroupToOrderByCreatedAtDesc(user_from, group_to, pageable);
        return messages;
    }

    @Override
    public Message DTOtoMessage(MessageDTO messageDTO, Long ToId, Boolean toGroup) {
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        switch(messageDTO.getType()) {
            case "TXT":
                message.setType(MessageType.TXT);
                break;
            case "AUD":
                message.setType(MessageType.AUD);
                break;
            case "VID":
                message.setType(MessageType.VID);
                break;
            case "IMG":
                message.setType(MessageType.IMG);
                break;
            case "DOC":
                message.setType(MessageType.DOC);
                break;
            case "ZIP":
                message.setType(MessageType.ZIP);
                break;
        }
        message.setContentURL(messageDTO.getContentURL());
        message.setOneTime(messageDTO.getOneTime());
        message.setUserFrom(userRepository.findByUsername(messageDTO.getUserFrom()).get());
        if (toGroup) {
            message.setGroupTo(groupRepository.findById(ToId).get());
        } else {
            message.setUserTo(userRepository.findById(ToId).get());
        }
        return message;
    }

}
