package com.chatapp.backend.model;

import com.chatapp.backend.enm.ConnectionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    private Long id;

    @Column(nullable = false)
    private Users user_from;

    @Column(nullable = false)
    private Users user_to;

    @Column(nullable = false)
    private ConnectionStatus status;
}
