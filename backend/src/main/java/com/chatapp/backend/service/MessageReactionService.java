package com.chatapp.backend.service;

import java.util.List;

import com.chatapp.backend.model.Message;
import com.chatapp.backend.model.MessageReaction;
import com.chatapp.backend.model.User;

public interface MessageReactionService {
    
    List<MessageReaction> getMessageReactions(Message message);
    MessageReaction getMessageReactionofUser(Message message, User user);
    MessageReaction saveMessageReaction(MessageReaction messageReaction);
    void updateMessageReaction(MessageReaction messageReaction);
    void deleteMessageReaction(Long id);

}
