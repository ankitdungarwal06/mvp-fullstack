package com.neelkanth.headerbackend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private Date dob;

    @Column
    private String addr1;

    @Column
    private String addr2;

    @Column
    private String addr3;

    @Column
    private String profession;

    @Column(nullable = false)
    private String username;

    @Column
    private String email;

    @Column
    private List<String> phoneNos;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference
    private List<ImportantDate> importantDates = new ArrayList<>();

    public void addImportantDate(ImportantDate date) {
        importantDates.add(date);
        date.setUser(this);
    }

    // Custom setter to ensure relationship consistency
    public void setImportantDates(List<ImportantDate> importantDates) {
        this.importantDates.clear();
        if (importantDates != null) {
            importantDates.forEach(this::addImportantDate);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", addr1='" + addr1 + '\'' +
                ", addr2='" + addr2 + '\'' +
                ", addr3='" + addr3 + '\'' +
                ", profession='" + profession + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNos=" + phoneNos +
                '}';
    }
}
