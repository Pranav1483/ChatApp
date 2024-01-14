package com.chatapp.backend.model;

import java.time.LocalDateTime;

import com.chatapp.backend.enm.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table (name = "User")
public class User {
    
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    @Pattern(regexp = "[A-Za-z]+", message = "Name should only contain alphabets")
    private String fname;

    @Column
    @Pattern(regexp = "[A-Za-z]*", message = "Name should only contain alphabets")
    private String lname;

    @Column(nullable = false, unique = true)
    @Email(message = "Invalid Email")
    private String email;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "[A-Za-z0-9_]+", message = "Username should only contain Alphabets, Numbers or Underscore")
    private String username;

    @Column(nullable = false)
    @Size(min = 60, max = 256)
    private String password;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private UserStatus status;

    @Column
    private LocalDateTime lastActive;

    @Column(nullable = false)
    private String profilePic;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        status = UserStatus.OFFLINE;
        profilePic = "default";
    }
}
