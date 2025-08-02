package com.neelkanth.headerbackend.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Pnr {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String pnrNumber;
    private LocalDate travelDate;
    private String status;
    private LocalDate lastChecked;
    private String trainNumber;
    private String[] users;
    private String coach;
    private String seat;
    private String trainName;

    @Version
    private Long version;
}
