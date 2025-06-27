package com.neelkanth.headerbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Data
public class ImportantDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String occasion;
    private Date date;
    private String restricted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Override
    public String toString() {
        return "ImportantDate{" +
                "id=" + id +
                ", occasion='" + occasion + '\'' +
                ", date=" + date +
                ", restricted='" + restricted + '\'' +
                '}';
    }
}