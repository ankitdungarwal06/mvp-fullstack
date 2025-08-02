package com.neelkanth.headerbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String status; //OPEN, CLOSED, IN-PROGRESS, PARKED, ABANDONED
    private String summary;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = true)
    private Event event;

    @OneToOne
    @JoinColumn(name = "task_id", nullable = true)
    private Task task;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    public Long getEventId() {
        return event != null ? event.getId() : null;
    }

    public Long getTaskId() {
        return task != null ? task.getId() : null;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
