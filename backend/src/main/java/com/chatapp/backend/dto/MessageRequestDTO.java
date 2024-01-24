package com.chatapp.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageRequestDTO {
    
    private MessageDTO messageDTO;
    private Long toId;
    private Boolean toGroup;
    
}
