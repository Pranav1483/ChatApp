package com.chatapp.backend.service;

import com.chatapp.backend.model.Connection;
import com.chatapp.backend.model.User;

public interface ConnectionService {
    
    Connection getConnectionByUsers(User userFrom, User userTo);
    Connection saveConnection(Connection connection);
    void updateConnection(Connection connection);

}
