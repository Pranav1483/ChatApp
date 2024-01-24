package com.chatapp.backend.dto;

import java.time.LocalDateTime;

import com.chatapp.backend.model.User;

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
    private String status;
    private String profilePic;
    private LocalDateTime lastActive;

    public UserDTOForUser(User user) {
        this.id = user.getId();
        this.fname = user.getFname();
        this.lname = user.getLname();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.createdAt = user.getCreatedAt();
        switch (user.getStatus()) {
            case OFFLINE:
                this.status = "OFFLINE";
                break;
            case ONLINE:
                this.status = "ONLINE";
        }
        this.profilePic = user.getProfilePic();
        this.lastActive = user.getLastActive();
    }
    
}
