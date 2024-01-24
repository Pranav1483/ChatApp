package com.chatapp.backend.service;

import java.util.List;

import com.chatapp.backend.model.LatestMessage;
import com.chatapp.backend.model.User;

public interface LatestMessageService {
    
    List<LatestMessage> getByUser(User user);
    LatestMessage getByUsers(User user, User inboxUser);
    LatestMessage saveLatestMessage(LatestMessage latestMessage);
    void updateLatestMessage(LatestMessage latestMessage);
    
}
