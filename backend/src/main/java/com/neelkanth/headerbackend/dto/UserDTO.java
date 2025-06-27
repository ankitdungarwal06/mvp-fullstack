package com.neelkanth.headerbackend.dto;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dob;
    private String addr1;
    private String addr2;
    private String addr3;
    private String profession;
    private String username;
    private String email;
    private List<String> phoneNos;
    private List<ImportantDateDTO> importantDates;
}
