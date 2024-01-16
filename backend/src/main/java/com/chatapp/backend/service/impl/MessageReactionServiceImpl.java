package com.chatapp.backend.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chatapp.backend.model.Message;
import com.chatapp.backend.model.MessageReaction;
import com.chatapp.backend.model.User;
import com.chatapp.backend.repository.MessageReactionRepository;
import com.chatapp.backend.service.MessageReactionService;

@Service
public class MessageReactionServiceImpl implements MessageReactionService{
    
    private MessageReactionRepository messageReactionRepository;

    public MessageReactionServiceImpl(MessageReactionRepository messageReactionRepository) {
        this.messageReactionRepository = messageReactionRepository;
    }

    @Override
    public List<MessageReaction> getMessageReactions(Message message) {
        List<MessageReaction> reactions = messageReactionRepository.findByMessage(message);
        return reactions;
    }

    @Override
    public MessageReaction getMessageReactionofUser(Message message, User user) {
        Optional<MessageReaction> reaction = messageReactionRepository.findByMessageAndUser(message, user);
        if (reaction.isPresent()) return reaction.get();
        else throw new NoSuchElementException("No Reaction Found");
    }

    @Override
    public MessageReaction saveMessageReaction(MessageReaction messageReaction) {
        return messageReactionRepository.save(messageReaction);
    }

    @Override
    public void updateMessageReaction(MessageReaction messageReaction) {
        messageReactionRepository.save(messageReaction);
    }

    @Override
    public void deleteMessageReaction(Long id) {
        messageReactionRepository.deleteById(id);
    }

}
