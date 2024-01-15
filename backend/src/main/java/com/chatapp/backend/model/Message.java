package com.chatapp.backend.model;

import java.time.LocalDateTime;

import com.chatapp.backend.enm.MessageStatus;
import com.chatapp.backend.enm.MessageType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "message")
public class Message {
    
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private MessageType type;

    @Column
    private String contentURL;

    @Column(nullable = false)
    private boolean oneTime;

    @Column(nullable = false)
    private User user_from;

    @Column
    private User user_to;

    @Column
    private Group group_to;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private MessageStatus status;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        status = MessageStatus.SENT;
    }
}
