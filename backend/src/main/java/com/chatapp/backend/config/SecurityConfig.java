package com.chatapp.backend.config;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityConfig extends WebSecurityConfiguration{
    
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
