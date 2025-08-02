package com.neelkanth.headerbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private String location;
    private String notes;
    private String category;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getter for userId to match frontend expectations
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    public String getUsername() {
        return user != null ? user.getUsername() : null;
    }
}
