package com.chatapp.backend.model;

import java.time.LocalDateTime;

import com.chatapp.backend.enm.ConnectionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Connection")
public class Connection {
    
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_from_id", nullable = false)
    private User user_from;

    @ManyToOne
    @JoinColumn(name = "user_to_id", nullable = false)
    private User user_to;

    @Column(nullable = false)
    private ConnectionStatus status;

    @Column(nullable = false)
    private LocalDateTime lastUpdate;

    @PrePersist
    public void prePersist() {
        lastUpdate = LocalDateTime.now();
    }
}
