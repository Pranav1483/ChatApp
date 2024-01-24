package com.chatapp.backend.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chatapp.backend.model.LatestMessage;
import com.chatapp.backend.model.User;
import com.chatapp.backend.repository.LatestMessageRepository;
import com.chatapp.backend.service.LatestMessageService;

@Service
public class LatestMessageServiceImpl implements LatestMessageService{
    
    private LatestMessageRepository latestMessageRepository;

    public LatestMessageServiceImpl(LatestMessageRepository latestMessageRepository) {
        this.latestMessageRepository = latestMessageRepository;
    }

    @Override
    public List<LatestMessage> getByUser(User user) {
        List<LatestMessage> latestMessages = latestMessageRepository.findByUserOrInboxUser(user, user);
        return latestMessages;
    }

    @Override
    public LatestMessage getByUsers(User user, User inboxUser) {
        Optional<LatestMessage> latestMessage = latestMessageRepository.findByUserAndInboxUserOrUserAndInboxUser(user, inboxUser, inboxUser, user);
        if (latestMessage.isPresent()) {
            return latestMessage.get();
        }
        else throw new NoSuchElementException();
    }

    @Override
    public LatestMessage saveLatestMessage(LatestMessage latestMessage) {
        if (latestMessage.getInboxUser() == null && latestMessage.getInboxGroup() == null) {
            throw new IllegalArgumentException("Both InboxUser and InboxGroup cannot be empty");
        }
        if (latestMessage.getInboxUser() != null && latestMessage.getInboxGroup() != null) {
            throw new IllegalArgumentException("Both InboxUser and InboxGroup cannot be filled");
        }
        return latestMessageRepository.save(latestMessage);
    }

    @Override
    public void updateLatestMessage(LatestMessage latestMessage) {
        if (latestMessage.getInboxUser() == null && latestMessage.getInboxGroup() == null) {
            throw new IllegalArgumentException("Both InboxUser and InboxGroup cannot be empty");
        }
        if (latestMessage.getInboxUser() != null && latestMessage.getInboxGroup() != null) {
            throw new IllegalArgumentException("Both InboxUser and InboxGroup cannot be filled");
        }
        latestMessageRepository.save(latestMessage);
    }

}
