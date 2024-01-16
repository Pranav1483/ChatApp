package com.chatapp.backend.dto;

import com.chatapp.backend.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTOForAnon {
    
    private Long id;
    private String fname;
    private String lname;
    private String username;

    public UserDTOForAnon(User user) {
        this.id = user.getId();
        this.fname = user.getFname();
        this.lname = user.getLname();
        this.username = user.getUsername();
    }

}
