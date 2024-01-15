package com.chatapp.backend.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chatapp.backend.model.Connection;
import com.chatapp.backend.model.User;
import com.chatapp.backend.repository.ConnectionRepository;
import com.chatapp.backend.service.ConnectionService;

@Service
public class ConnectionServiceImpl implements ConnectionService{
    
    private ConnectionRepository connectionRepository;

    public ConnectionServiceImpl(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    @Override
    public Connection getConnectionByUsers(User userFrom, User userTo) {
        Optional<Connection> connection = connectionRepository.findByUserFromAndUserTo(userFrom, userTo);
        if (connection.isPresent()) return connection.get();
        else throw new NoSuchElementException("No connection found");
    }

    @Override
    public Connection saveConnection(Connection connection) {
        return connectionRepository.save(connection);
    }

    @Override
    public void updateConnection(Connection connection) {
        connectionRepository.save(connection);
    }

}
