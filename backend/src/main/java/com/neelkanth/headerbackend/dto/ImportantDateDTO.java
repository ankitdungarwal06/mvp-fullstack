package com.neelkanth.headerbackend.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class ImportantDateDTO {
    private Long id;
    private String occasion;
    private Date date;
    private String restricted;
}
