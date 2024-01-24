package com.chatapp.backend.service;

import com.chatapp.backend.model.User;

public interface UserService {

    User getUserById(Long id);
    String getUsernameFromId(Long id);
    User saveUser(User user);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    void updateUser(User user);

}
