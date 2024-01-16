package com.chatapp.backend.dto;

import java.time.LocalDateTime;

import com.chatapp.backend.enm.UserStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTOForUser {
    
    private Long id;
    private String fname;
    private String lname;
    private String email;
    private String username;
    private LocalDateTime createdAt;
    private UserStatus status;
    private String profilePic;
    
}
