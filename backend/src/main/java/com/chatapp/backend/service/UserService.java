package com.chatapp.backend.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.chatapp.backend.model.User;

public interface UserService {

    User saveUser(User user);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    void updateUser(User user);
    Collection<? extends GrantedAuthority> getAuthorities();
    String getPassword();
    String getUsername();
    boolean isAccountNonExpired();
    boolean isAccountNonLocked();
    boolean isCredentialsNonExpired();
    boolean isEnabled();

}
