package com.chatapp.backend.dto;

import java.time.LocalDateTime;

import com.chatapp.backend.model.Message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    
    private Long id;
    private String content;
    private String type;
    private String contentURL;
    private Boolean oneTime;
    private String userFrom;
    private LocalDateTime createdAt;

    public MessageDTO(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        switch (message.getType()) {
            case TXT:
                this.type = "TXT";
                break;
            case AUD:
                this.type = "AUD";
                break;
            case VID:
                this.type = "VID";
                break;
            case IMG:
                this.type = "IMG";
                break;
            case DOC:
                this.type = "DOC";
                break;
            case ZIP:
                this.type = "ZIP";
                break;
        }
        this.contentURL = message.getContentURL();
        this.oneTime = message.getOneTime();
        this.userFrom = message.getUserFrom().getUsername();
        this.createdAt = message.getCreatedAt();
    }
    
}
